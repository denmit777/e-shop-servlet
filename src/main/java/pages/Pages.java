package pages;

import model.Good;
import service.GoodService;
import service.OrderService;
import service.UserService;
import service.impl.GoodServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

public final class Pages {

    private static final UserService userService = new UserServiceImpl();
    private static final GoodService goodService = new GoodServiceImpl();
    private static final OrderService orderService = new OrderServiceImpl();

    private static String header() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "   <head>\n" +
                "       <meta charset=\"UTF-8\">\n" +
                "       <title>https://www.online-shop.com</title>\n" +
                "   </head>\n";
    }

    public static void loginPage(PrintWriter writer) {
        writer.println(header() +
                "   <body>\n" +
                "       <div style=\"text-align: center\">\n" +
                "       <form method=\"post\" action=\"e-shop\">\n" +
                "           <p><h2>Welcome to Online Shop!</h2>\n" +
                "               <input name=\"login\" type=\"text\" size=\"40\" required placeholder=\"Enter your name\">" +
                "           </p>\n" +
                "           <p>" +
                "               <input name=\"password\" type=\"password\" size=\"40\" required placeholder=\"Enter your password\">" +
                "           </p>\n" +
                "           <p>" +
                "               <label>\n" +
                "                   <input type=\"checkbox\" name=\"isUserCheck\" value=\"yes\">\n" +
                "               </label>I agree with the terms of service" +
                "           </p>\n" +
                "           <p><input name=\"submit\" type=\"submit\" value=\"Enter\"></p>\n" +
                "           <p><input name=\"submit\" type=\"submit\" value=\"Register\"></p>\n" +
                "       </form>\n" +
                "       </div>\n" +
                "   </body>\n" +
                "</html>");
    }

    public static void registerPage(PrintWriter writer) {
        writer.println(header() +
                "   <body>" +
                "       <h2 align=\"center\">You're unregistered user. Please, register right now</h2>\n" +
                "       <form action=\"/register\" align=\"center\" method=\"post\">\n" +
                "           <table align=\"center\">\n" +
                "               <tr>\n" +
                "                   <td>Login</td>\n" +
                "                   <td><input type=\"text\" name=\"login\" placeholder=\"Enter your name\"/></td>\n" +
                "               </tr>\n" +
                "               <tr>\n" +
                "                   <td>Password</td>\n" +
                "                   <td><input type=\"password\" name=\"password\" placeholder=\"Enter your password\"/></td>\n" +
                "               </tr>\n" +
                "               <tr>\n" +
                "                   <td><input type=\"submit\" value=\"Sign In\"/></td>\n" +
                "               </tr>\n" +
                "           </table>\n" +
                "       </form>" +
                "   </body>\n" +
                "</html>");

    }

    public static void goodPage(PrintWriter writer, HttpSession session) {
        List<Good> goods = goodService.getAll();
        String chosenGoods = (String) session.getAttribute("chosenGoods");

        String login = (String) session.getAttribute("login");
        String choice = goodService.getChoice(chosenGoods);
        String options = goodService.getStringOfOptionsForDroppingMenuFromGoodList(goods);

        writer.println(header() +
                "   <body>\n" +
                "       <h2 align=\"center\">Hello <span>" + login + "</span>!</h2>\n" +
                "       <div align=\"center\">\n" +
                "           <h2>\n" +
                "               <pre>" + choice + "</pre>\n" +
                "           </h2>\n" +
                "       </div>\n" +
                "       <form action=\"/goods\" method=\"post\" align=\"center\">\n" +
                "           <select name=\"goodName\" id=\"goodName\">" +
                                options +
                "           </select><br/><br/>\n" +
                "           <input name=\"submit\" type=\"submit\" value=\"Add Good\">\n" +
                "           <input name=\"submit\" type=\"submit\" value=\"Submit\">\n" +
                "               <br/><br/>\n" +
                "           <input name=\"submit\" type=\"submit\" value=\"Remove Good\">\n" +
                "           <input name=\"submit\" type=\"submit\" value=\"Log out\">\n" +
                "       </form>\n" +
                "   </body>\n" +
                "</html>");
    }

    public static void orderPage(PrintWriter writer, HttpSession session) {
        String login = (String) session.getAttribute("login");
        String order = (String) session.getAttribute("order");
        BigDecimal totalPrice = (BigDecimal) session.getAttribute("totalPrice");
        String orderResult = orderService.orderResult(totalPrice);

        writer.println(header() +
                "   <body>\n" +
                "       <h2 align=\"center\">Dear <span>" + login + "</span>, " + orderResult + "</h2>\n" +
                "       <div align=\"center\">\n" +
                "           <h2>\n" +
                "               <pre>" + order + "</pre>\n" +
                "           </h2>\n" +
                "       </div>\n" +
                "       <h2 align=\"center\">Total: $ <span>" + totalPrice + "</span></h2>\n" +
                "       <form action=\"/e-shop\" method=\"get\" align=\"center\">\n" +
                "           <input name=\"submit\" type=\"submit\" value=\"Log out\">\n" +
                "       </form>" +
                "   </body>\n" +
                "</html>");
    }

    public  static void successPage(PrintWriter writer, HttpSession session) {
        String login = (String) session.getAttribute("login");

        writer.println(header() +
                "   <body>\n" +
                "       <h1 align=\"center\">Welcome to Online Shop, <span>" + login + "</span>!</h1>\n" +
                "       <h2 align=\"center\">Look our <a href=\"http://localhost:8081/goods\">goods</a></h2>\n" +
                "   </body>" +
                "</html>");
    }

    public static void errorPage(PrintWriter writer) {
        writer.println(header() +
                "   <body>\n" +
                "       <div align=\"center\">\n" +
                "           <h1>Oops!</h1>\n" +
                "           <h3>You should not be here</h3>\n" +
                "           <h3>Please, agree with the terms of service first</h3>\n" +
                "           <h3><a href=\"http://localhost:8081/e-shop\">Start page</a></h3>\n" +
                "       </div>" +
                "   </body>\n" +
                "</html>");
    }

    public  static void registerErrorPage(PrintWriter writer, HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String invalidUser = userService.invalidUser(login, password);

        writer.println(header() +
                "   <body>\n" +
                "       <div align=\"center\">\n" +
                "           <h2>" + invalidUser + "</h2>\n" +
                "           <h2>Please, try again <a href=\"http://localhost:8081/register\">here</a></h2>\n" +
                "       </div>\n" +
                "   </body>\n" +
                "</html>");
    }
}
