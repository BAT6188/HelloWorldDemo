package ooopen.hessian.test;


public class HessionServer {

    public static void main(String[] args){
        Server server = new Server(9011);
        ServletHolder servletHolder = new ServletHolder(new HelloImp());
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.addServlet(servletHolder, "/hessian");
        context.setInitParameter("home-class", HelloImp.class.getName());
        context.setInitParameter("home-api", Hello.class.getName());

        server.setHandler(context);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
