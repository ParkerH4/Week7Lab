package servlets;

import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.User;

/**
 *
 * @author user
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            UserDB userDB = new UserDB();
            ArrayList<User> users = new ArrayList<>();

            if (users != null) {
                users = null;
            }
            users = userDB.getAll();
            request.setAttribute("users", users);

        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        UserDB userDB = new UserDB();

        switch (action) {
            case "add":
                String email = request.getParameter("addEmail");
                String firstname = request.getParameter("addFirst");
                String lastname = request.getParameter("addLast");
                String password = request.getParameter("addPass");
                int role = Integer.parseInt(request.getParameter("addRole"));

                User user = new User(email, firstname, lastname, password, role);
                try {
                    userDB.insert(user);
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "edit":
                String editedEmail = request.getParameter("editEmail");
                String editedFirst = request.getParameter("editFirst");
                String editedLast = request.getParameter("editLast");
                String editedPass = request.getParameter("editPass");
                String editedRole = request.getParameter("editRole");

                request.setAttribute("editEmail", editedEmail);
                request.setAttribute("editFirst", editedFirst);
                request.setAttribute("editLast", editedLast);
                request.setAttribute("editPass", editedPass);
                request.setAttribute("editRole", editedRole);
                break;
            case "delete":
                User deletedUser = null;
                String deletedEmail = request.getParameter("deleteEmail");
                try {
                    deletedUser = userDB.get(deletedEmail);
                    userDB.delete(deletedUser);
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "save":
                String savedEmail = request.getParameter("editEmail");
                String savedFirst = request.getParameter("editFirst");
                String savedLast = request.getParameter("editLast");
                String savedPass = request.getParameter("editPass");
                int savedRole = Integer.parseInt(request.getParameter("editRole"));

                User user1 = new User(savedEmail, savedFirst, savedLast, savedPass, savedRole);
                try {
                    userDB.update(user1);
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case "cancel":
                request.setAttribute("editEmail", "");
                request.setAttribute("editFirst", "");
                request.setAttribute("editLast", "");
                request.setAttribute("editPass", "");
                request.setAttribute("editRole", "");
                break;
        }
    }
}
