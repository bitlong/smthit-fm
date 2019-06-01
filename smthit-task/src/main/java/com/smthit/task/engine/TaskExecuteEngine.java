/**
 * 
 */
package com.smthit.task.engine;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;

import com.smthit.task.SmthitTaskFactory;
import com.smthit.task.core.biz.TaskService;
import com.smthit.task.engine.event.TaskCancelEvent;
import com.smthit.task.engine.event.TaskTerminalEvent;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Bean
 * 任务的执行引擎，消费者使用内置的线程池
 * @since 1.0.4
 */
@Slf4j
public class TaskExecuteEngine {
	
	private final BlockingQueue<Runnable> queue;

	private ThreadPoolExecutor threadPoolExecutor;
	private int poolSize = 10;
	
	@Getter
	private AtomicInteger executedTaskCount;	
	@Autowired
	private TaskService taskService;
	
	public TaskExecuteEngine() {
		queue = new ArrayBlockingQueue<>(2000);
		init();
	}
	
	/**
	 * 初始化任务桶
	 * @since 1.0.4
	 */
	@PostConstruct
	public void init() {
		executedTaskCount = new AtomicInteger(0);

		//创建线程池
		threadPoolExecutor = new ThreadPoolExecutor(this.poolSize,
				this.poolSize,
				60L,
				TimeUnit.SECONDS,
				queue,
				new TaskThreadFactory()
		);

		threadPoolExecutor.setRejectedExecutionHandler((r, executor) -> {
			log.warn("TODO 任务被拒绝");
		});
		//清理遗留的已经挂起的任务
	}
	
	@PreDestroy
	public void destory() {
		queue.forEach(e -> {
			Consumer c = (Consumer)e;
			TaskTerminalEvent taskEventCancel = new TaskTerminalEvent(c.executor.getContext(), c.executor.getTask());						
			TaskEventBusFactory.getTaskEventBus().post(taskEventCancel);
		});
	}
	
	public void cancelTask(String taskNo) {
		queue.forEach(r -> {
			Consumer c = (Consumer)r;
			if(taskNo.equals(c.executor.getTask().getTaskNo())) {
				queue.remove(c);
				TaskCancelEvent taskEventCancel = new TaskCancelEvent(c.executor.getContext(), c.executor.getTask());
				TaskEventBusFactory.getTaskEventBus().post(taskEventCancel);
			}
		});
	}

	/**
	 * @since 1.0.4
	 * @param data
	 */
	public void addTaskEvent(Task task) {
		List<AbstractTaskExecutor> executors = SmthitTaskFactory.getSmthitTaskFactory().getAvailableTaskExecutors(task.getTaskKey());
		if(executors.isEmpty()) {
			throw new TaskException("没有任何可以执行的任务执行器");
		}

		//1.任务落地, 先落地;
		taskService.addTask(task);
		
		//2.任务入队，等待执行
		executors.forEach(e -> {
			TaskContext taskContext = new TaskContext();
			taskContext.setTask(task);

			e.setContext(taskContext);
			e.setTask(task);
			
			threadPoolExecutor.execute(new Consumer(e));

		});
		
		log.debug("成功入队, size: " + queue.size() +",  数据:" + task);
	}
	
	public class Consumer implements Runnable {
		private AbstractTaskExecutor executor;

		public Consumer(AbstractTaskExecutor executor) {
			this.executor = executor;
		}

		@Override
		public void run() {
			log.debug("Consume Execute, Size: " + queue.size() + ", remainingCapacity: " + queue.remainingCapacity());
			executedTaskCount.incrementAndGet();
			executor.run();
		}
	}
}
