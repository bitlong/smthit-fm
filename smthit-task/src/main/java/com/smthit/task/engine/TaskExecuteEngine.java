/**
 * 
 */
package com.smthit.task.engine;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.smthit.task.data.Task;
import com.smthit.task.engine.demo.DemoTaskExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bean
 * 任务的执行引擎，消费者使用内置的线程池
 */
@Slf4j
public class TaskExecuteEngine {
	
	private final BlockingQueue<Task> queue;
	/**
	 * 消费者线程
	 */
	private Thread consumerThread;
	
	/**
	 * 消费者的数量
	 */
	private int cosumerCount = 1;
	
	public TaskExecuteEngine() {
		queue = new ArrayBlockingQueue<Task>(2000);
		consumerThread = new Thread(new Consumer(queue));
		consumerThread.start();
	}

	public void addEventData(Task data) {
		
		//1.任务落地, 先落地;
		
		
		//2.任务入队，等待执行
		boolean flag = queue.offer(data);
		
		if (flag) {
			log.info("成功入队, size: " + queue.size() +",  数据:" + data);
		} else {
			log.error("失败入队, size: " + queue.size() +"remainingCapacity: " + queue.remainingCapacity() + ",  数据:" + data);
		}
	}
	
	public class Consumer implements Runnable {
		private BlockingQueue<Task> queue;

		public Consumer(BlockingQueue<Task> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			try {
				while (true) {
					log.info("Consume Execute, Size: " + queue.size() + "remainingCapacity:" + queue.remainingCapacity());
					consume(queue.take());
				} // 当队列空时，消费者阻塞等待
			} catch (InterruptedException ex) {
				log.error(ex.getMessage(), ex);
			} catch (Exception exp) {
				log.error("钉钉事件消费线程,异常:" + exp);
			}
		}

		private void consume(Task task) {
			log.info("处理回调钉钉回调事件: " + task);			
			/**
			 * 获取到Task，创建TaskExecutor实例，生成任务上下文，让后执行;
			 */
			TaskContext taskContext = new TaskContext();
			AbstractTaskExecutor taskExecutor = new DemoTaskExecutor();
			taskExecutor.setContext(taskContext);
			taskExecutor.setTask(task);
			
			taskExecutor.run(taskContext, task);
			
		}
	}
}
