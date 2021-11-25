package io.github.kavahub.learnjava.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * {@link FutureTask} 在高并发环境下确保任务只执行一次
 * 
 * <p>
 * 使用 {@link FutureTask} 提高了创建连接的效率，可以并行创建连接。在高并发环境下，效率明显提升
 */
public class ConnectionPool {
    private ConcurrentHashMap<String,FutureTask<Connection>> connectionPool = new ConcurrentHashMap<String, FutureTask<Connection>>();
    
        public Connection getConnection(String key) {
            try {
                return getConnection0(key);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        private Connection getConnection0(final String key) throws InterruptedException, ExecutionException{
            FutureTask<Connection>connectionTask=connectionPool.get(key);
            if(connectionTask!=null){
                return connectionTask.get();
            }
            else{
                Callable<Connection> callable = new Callable<Connection>(){
                    @Override
                    public Connection call() throws Exception {
                        return createConnection(key);
                    }
                };
                FutureTask<Connection> newTask = new FutureTask<Connection>(callable);
                connectionTask = connectionPool.putIfAbsent(key, newTask);
                if(connectionTask == null){
                    connectionTask = newTask;
                    connectionTask.run();
                }
                return connectionTask.get();
            }
        }
        
        //创建Connection
        private Connection createConnection(String key){
            return new Connection(key);
        }
}
