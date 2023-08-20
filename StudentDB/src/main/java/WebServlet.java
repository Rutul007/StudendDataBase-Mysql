import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


 
 public class WebServlet  extends HttpServlet {

  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
       
    try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("loaded driver succesfull");   
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql","root","200160107092");
            Statement st=cn.createStatement();
            st.executeUpdate("create database if not exists student");
            st.executeUpdate("use student");
            st.execute("create table if not exists student2 (s_enrollno BIGINT,s_firstname varchar(20),s_lastname varchar(20),s_branch varchar(10),s_mobileno BIGINT)");
            
            ResultSet rs ;
            
        
           String s2 = request.getParameter("s1");
          
           Long eno = Long.valueOf(request.getParameter("s_enrollno"));
           
           String fname =request.getParameter("s_firstname");
           String lname = request.getParameter("s_lastname");
           String branch =request.getParameter("s_branch");
           String mobile=request.getParameter("s_mobileno");
          
           PreparedStatement sr;
           
           Statement stmt = cn.createStatement();
            ResultSet res;
                 
            // For Insert Data......
            switch (s2) {
            //For Delete Data......
                case "Insert Data":
                    String str = "insert into student2 values(?,?,?,?,?)";
                    sr = cn.prepareStatement(str);
                    sr.setLong(1,eno);
                    sr.setString(2,fname);
                    sr.setString(3,lname);
                    sr.setString(4,branch);
                    sr.setString(5,mobile);
                    int i = sr.executeUpdate();
                    out.println("Student Data inserted");
                    cn.close();
                    break;
                case "Delete Data":
                    String str1 = "select s_enrollno from student2 where s_enrollno = "+eno+" ";
                    rs =stmt.executeQuery(str1);
                    if(rs.next())
                    {
                        str1 = "delete from student2 where s_enrollno = ?";
                        sr = cn.prepareStatement(str1);
                        sr.setLong(1,eno);
                        sr.executeUpdate();
                        out.println("Student Data Deleted");
                    }
                    else{
                        out.println("Student Data Not Valid");
                    }       cn.close();
                    break;
                case "Update Data":
                    String str3 = "select * from student2 where s_enrollno = "+eno+"";
                    rs =stmt.executeQuery(str3);
                    if(rs.next())
                    {
                        str3= "update student2 set s_enrollno=? , s_firstname=?,s_lastname=?,s_branch=?,s_mobileno=? where s_enrollno = "+eno+"";
                        sr=cn.prepareStatement(str3);
                        sr.setLong(1,eno);
                        sr.setString(2,fname);
                        sr.setString(3,lname);
                        sr.setString(4,branch);
                        sr.setString(5,mobile);
                        
                        sr.executeUpdate();
                        
                        out.println("Update Student2 Data Succesfull");
                        cn.close();
                    }
                    else{
                        out.println("Student Data Not Valid");
                    }   break;
                case "View Data":
                    res = stmt.executeQuery("select * from student2 where s_enrollno="+eno+"");
                    if(res.next()){
                        Long enu= res.getLong("s_enrollno");
                        String fname1= res.getString("s_firstname");
                        String lname1= res.getString("s_lastname");
                        String branch1= res.getString("s_branch");
                        String mb1= res.getString("s_mobileno");
                        out.println("<html><body>");
                        out.println("Enrollment No:- "); out.println(enu);out.println("<br>");
                        out.println("First Name:- "); out.println(fname1);out.println("<br>");
                        out.println("Last Name:- "); out.println(lname1);out.println("<br>");
                        out.println("Branch:- "); out.println(branch1); out.println("<br>");
                out.println("Mobile No:- ");out.println(mb1);out.println("<br>");
               out.println("</html></body>");
            }
               else{
               out.println("Student Data is Not Valid");
               }    break;
            }
       }catch(ClassNotFoundException | SQLException e   )
       { System.out.println(e); }}}