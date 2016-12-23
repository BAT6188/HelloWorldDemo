package ooopen.hessian.test;


import com.caucho.hessian.server.HessianServlet;

public class HelloImp extends HessianServlet implements Hello{



    @Override
    public void sayHello(User user) {
        System.out.println("sayHello:" + user);
    }

    @Override
    public User getCurUser() {
        User2 user = new User2();
        user.setName("HessionServer");
        user.setAge(1);
        return user;
    }
}
