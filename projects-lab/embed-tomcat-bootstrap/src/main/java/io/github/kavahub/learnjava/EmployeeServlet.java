package io.github.kavahub.learnjava;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 雇员http接口
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@WebServlet(
        name = "EmployeeServlet",
        urlPatterns = {"/employee"}
)
@Slf4j
public class EmployeeServlet extends HttpServlet {

        EmployeeService employeeService = new EmployeeService();
    
        @Override
        public void init() throws ServletException {
            super.init();
            log.info(">>>>> EmployeeServlet init ");
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String action = req.getParameter("searchAction");
            if (action!=null){
                switch (action) {           
                case "searchById":
                    searchEmployeeById(req, resp);
                    break;           
                case "searchByName":
                    searchEmployeeByName(req, resp);
                    break;
                }
            }else{
                List<Employee> result = employeeService.getAllEmployees();
                forwardListEmployees(req, resp, result);
            }
        }
    
        private void searchEmployeeById(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            long idEmployee = Integer.valueOf(req.getParameter("idEmployee"));
            Employee employee = null;
            try {
                employee = employeeService.getEmployee(idEmployee);
            } catch (Exception ex) {
                Logger.getLogger(EmployeeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            req.setAttribute("employee", employee);
            req.setAttribute("action", "edit");
            String nextJSP = "/jsp/new-employee.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(req, resp);
        }
        
        private void searchEmployeeByName(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            String employeeName = req.getParameter("employeeName");
            List<Employee> result = employeeService.searchEmployeesByName(employeeName);        
            forwardListEmployees(req, resp, result);
        }
    
        private void forwardListEmployees(HttpServletRequest req, HttpServletResponse resp, List<Employee> employeeList)
                throws ServletException, IOException {
            String nextJSP = "/jsp/list-employees.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            req.setAttribute("employeeList", employeeList);
            dispatcher.forward(req, resp);
        }   
        
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            String action = req.getParameter("action");
            switch (action) {
                case "add":
                    addEmployeeAction(req, resp);
                    break;
                case "edit":
                    editEmployeeAction(req, resp);
                    break;            
                case "remove":
                    removeEmployeeByName(req, resp);
                    break;            
            }
    
        }
    
        private void addEmployeeAction(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            String name = req.getParameter("name");
            String lastName = req.getParameter("lastName");
            String birthday = req.getParameter("birthDate");
            String role = req.getParameter("role");
            String department = req.getParameter("department");
            String email = req.getParameter("email");
            Employee employee = new Employee(name, lastName, birthday, role, department, email);
            long idEmployee = employeeService.addEmployee(employee);
            List<Employee> employeeList = employeeService.getAllEmployees();
            req.setAttribute("idEmployee", idEmployee);
            String message = "The new employee has been successfully created.";
            req.setAttribute("message", message);
            forwardListEmployees(req, resp, employeeList);
        }
    
        private void editEmployeeAction(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            String name = req.getParameter("name");
            String lastName = req.getParameter("lastName");
            String birthday = req.getParameter("birthDate");
            String role = req.getParameter("role");
            String department = req.getParameter("department");
            String email = req.getParameter("email");
            long idEmployee = Integer.valueOf(req.getParameter("idEmployee"));
            Employee employee = new Employee(name, lastName, birthday, role, department, email, idEmployee);
            employee.setId(idEmployee);
            boolean success = employeeService.updateEmployee(employee);
            String message = null;
            if (success) {
                message = "The employee has been successfully updated.";
            }
            List<Employee> employeeList = employeeService.getAllEmployees();
            req.setAttribute("idEmployee", idEmployee);
            req.setAttribute("message", message);
            forwardListEmployees(req, resp, employeeList);
        }  
    
        private void removeEmployeeByName(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            long idEmployee = Integer.valueOf(req.getParameter("idEmployee"));
            boolean confirm = employeeService.deleteEmployee(idEmployee);
            if (confirm){
                String message = "The employee has been successfully removed.";
                req.setAttribute("message", message);
            }
            List<Employee> employeeList = employeeService.getAllEmployees();
            forwardListEmployees(req, resp, employeeList);
        }
     
}
