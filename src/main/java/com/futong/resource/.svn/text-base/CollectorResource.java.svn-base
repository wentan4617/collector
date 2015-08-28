package com.futong.resource;

import java.util.List;
import java.util.Set;

import javax.inject.Singleton;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.junit.runners.Parameterized.Parameters;

import com.futong.dao.BaseDao;
import com.futong.domain.Host;
import com.futong.domain.LogFile;
import com.futong.domain.LogFileRest;
import com.futong.domain.User;
import com.futong.server.TaskServer;

/**
 * 
 * @author went
 * @describe 将系统信息资源化，供外部调用
 * 
 *           需要实现的功能： 1、采集主机列表 ok
 *           				 2、每个主机的采集日志列表 ok
 *           				 3、当前调度任务列表 ok
 *           4、1和2中资源的编辑，包括，增删改
 *           
 *           5、3中资源的编辑 包括，增删改(当前版本不需要，因为状态全是从库中取)
 * 
 * 
 */

@Singleton
@Path("col")
public class CollectorResource {
	private BaseDao dao = new BaseDao();
	private TaskServer server = TaskServer.getInstance();
	//默认方法
	@GET
	@Path("/loghost")
	@Produces({ "application/json", "application/xml" })
	public List<Host> getMyResources() {
		List<Host> hosts = dao.getAllHost();
		return hosts;
	}

	@POST
	@Path("/loghost")
	@Consumes({ "application/json", "application/xml" })
	@Produces( {"application/json", "application/text" })
	public Response addLogHost(Host host){
		Response rp = null;
		try {
			if(!dao.checkUnique(host.getIp())){
				return Response.serverError().build();
			} else {
				dao.addHost(host);
				rp = Response.ok().build();
			} 
		} catch (Exception e) {
			rp = Response.serverError().build();
			e.printStackTrace();
		}
		return rp;
	}
	
	@DELETE
	@Path("/loghost/{ip}")
	@Produces( {"application/json", "application/text" })
	public Response deleteHostByIp(@PathParam("ip") String ip ){
		Response rp;
		try {
			dao.deleteHost(ip);
			rp = Response.ok().build();
		} catch (Exception e) {
			rp = Response.serverError().build();
			e.printStackTrace();
		}
		return rp;
	}
	
	@PUT
	@Path("/loghost")
	@Consumes({ "application/json", "application/xml" })
	@Produces( {"application/json", "application/text" })
	public Response updateLogHost(Host host ){
		Response rp = null;
		try {
			if(dao.checkUnique(host.getIp())){
				return Response.serverError().build();
			} else {
				dao.updateHost(host);
				rp = Response.ok().build();
			}
		} catch (Exception e) {
			rp = Response.serverError().build();
			e.printStackTrace();
		}
		return rp;
	}
	
	//=================logFile的操作====================
	
	@GET
	@Path("/logfile/{ip}")
	@Produces({"application/json"})
	public List<LogFile> getLogFilesByHostIp(@PathParam("ip") String hostIp){
		List<LogFile> logfiles = dao.getLogfilesByHostId(hostIp);
		return logfiles;
	}
	
	@POST
	@Path("/logfile")
	@Produces({"application/json"})
	@Consumes({ "application/json", "application/xml" })
	public Response addLogFile(LogFileRest logfileRest){
		LogFile logFile = this.RestToBean(logfileRest);
		Response rp = null;
		try {
			if(dao.addLogfile(logFile)){
				rp = Response.ok().build();
			}
		} catch (Exception e) {
			rp = Response.serverError().build();
			e.printStackTrace();
		}
		return rp;
	}
	
	
	@DELETE
	@Path("/logfile/{ip}/{logName}")
	@Produces({"application/json"})
	public Response deleteLogFileByHostId(@PathParam("ip") String ip,@PathParam("logName") String logName){
		
		Response rp = Response.ok().build();
		return rp;
	}
	
	@PUT
	@Path("/logfile")
	@Produces({"application/json"})
	@Consumes({ "application/json", "application/xml" })
	public Response updateLogFile(LogFileRest logfileRest){
		LogFile logFile = this.RestToBean(logfileRest);
		Response rp = null;
		try {
			if(dao.checkUnique(logFile.getHostIp())){
				return Response.serverError().build();
			} else {
				dao.updateLogFile(logFile);
				rp = Response.ok().build();
			}
		} catch (Exception e) {
			rp = Response.serverError().build();
			e.printStackTrace();
		}
		return rp;
	}
	
	@GET
	@Path("/jobs")
	@Produces({ "application/json", "application/xml" })
	public List<LogFile> getAllRunningJobs() {
		return server.getAllJobs();
	}
	//==========================以下是工具方法================================
	
	private LogFile RestToBean(LogFileRest r) {
		LogFile f = new LogFile();
		f.setCurrentCount(r.getCurrentCount());
		f.setCurrentSize(r.getCurrentSize());
		f.setFirstStartTime(r.getFirstStartTime());
		f.setHostIp(r.getHostIp());
		f.setInterval(r.getInterval());
		f.setLastCount(r.getLastCount());
		f.setLastModify(r.getLastModify());
		f.setLastSize(r.getLastSize());
		f.setLastStartTime(r.getLastStartTime());
		f.setLogName(r.getLogName());
		f.setLogState(r.getLogState());
		f.setLogType(r.getLogType());
		f.setTotalCount(r.getTotalCount());
		return f;
	}
	
	//==========================以下是测试内容=================================
	
	@GET
	@Path("/rs")
	@Produces({ "application/json", "application/xml" })
	public Response getResources() {
		List<Host> hosts = dao.getAllHost();
		Response rp=Response.ok(hosts).build();
//				.entity(hosts)
//				.header("Access-Control-Allow-Origin", "*")
		return rp;
		
	}
//
//	@GET
//	@Path("/list")
//	@Produces({ "application/json", "application/xml" })
//	public List<User> getListOfUsers() {
//		List<User> users = UserService.getUsers();
//		return users;
//	}
//
//	@GET
//	@Path("/{id}")
//	@Produces({ "application/json" })
//	public User getUser(@PathParam("id") String id) {
//		User u = UserService.getUserById(id);
//		return u;
//	}
//
//	@PUT
//	// @Path("/ids/{id}")
//	@Consumes({ "application/json", "application/xml" })
//	public void putUser(User user) {
//
//		UserService.updateUser(user);
//
//	}
//
//	@POST
//	// @Path("/ids/{id}")
//	@Consumes({ "application/json", "application/xml" })
//	public void postUser(User user) {
//
//		UserService.addUser(user);
//
//	}
//
//	@DELETE
//	@Path("/{id}")
//	public void deleteUser(@PathParam("id") String id) {
//		UserService.delUserById(id);
//	}
	
	//跨域访问解决方法
	@Path("searchConceptByKeyword")
	@Produces( {"application/json", "application/text", "application/rdf" })
	@Consumes("text/plain")
	@GET
	public Response searchConceptByKeyword(@QueryParam("searchString") String searchString,@QueryParam("format") String format,@QueryParam("searchMode") String searchMode)
	{
		Response rp=Response.ok()
				
				.header("Content-type","application/rdf+xml").header("Access-Control-Allow-Origin", "*").build();
		return rp;
	}

}
