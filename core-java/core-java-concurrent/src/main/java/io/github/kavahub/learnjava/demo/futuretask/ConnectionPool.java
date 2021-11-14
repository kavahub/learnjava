package io.github.kavahub.learnjava.demo.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask不能使用run方法重复执行
 */
public class ConnectionPool {
    private ConcurrentHashMap<String,FutureTask<Connection>>connectionPool = new ConcurrentHashMap<String, FutureTask<Connection>>();
    
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
