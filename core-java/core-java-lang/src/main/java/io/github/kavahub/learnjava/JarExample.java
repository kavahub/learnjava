package io.github.kavahub.learnjava;

public class JarExample {
    public static void main(String[] args) {
        System.out.println("Hello, JarExample!");
        
        if(args == null) {
            System.out.println("You have not provided any arguments!");
        }else {
            System.out.println("There are "+args.length+" argument(s)!");
            for(int i=0; i<args.length; i++) {
                System.out.println("Argument("+(i+1)+"):" + args[i]);
            }
        }
    }  
}
