package com.atguigu.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.Ticket;

public interface MemberDao {

	@Select("select * from t_member where loginacct=#{loginacct}")
	Member queryMemberByLogin(String loginacct);

	@Select("select * from t_ticket where memberid=#{id} and status='0'")
	Ticket queryTicketByMemberid(Integer id);

	@Insert("insert into t_ticket(memberid,piid,status,pstep) values(#{memberid},#{piid},#{status},#{pstep})")
	void saveTicket(Ticket ticket);

	@Update("update t_member set accttype=#{accttype} where id=#{memberId}")
	int updateAccttype(Map<String, Object> paramMap);

	@Update("update t_ticket set pstep=#{pstep} where id=#{id}")
	void updateTicketPstep(Ticket ticket);

	@Update("update t_member set realname=#{realname},cardnum=#{cardnum},tel=#{tel} where id=#{id}")
	int updateBasicinfo(Member loginMember);

	@Select("SELECT t_cert.* FROM t_cert,t_account_type_cert WHERE t_account_type_cert.accttype = #{accttype} AND t_account_type_cert.certid=t_cert.id")
	List<Cert> queryCertByAccttype(String accttype);

	@Select("select * from t_member where id=#{memberid}")
	Member getMemberById(Integer memberid);

	void saveMemberCertList(@Param("certList") List<MemberCert> certList);

	@Update("update t_member set email=#{email} where id=#{id}")
	int updateEmail(Member loginMember);

	@Update("update t_ticket set pstep=#{pstep},authcode=#{authcode} where id=#{id}")
	void updateTicketPstepAndAuthcode(Ticket ticket);

	@Update("update t_member set authstatus=#{authstatus} where id=#{id}")
	void updateAuthstatus(Member loginMember);

	@Update("update t_ticket set status=#{status} where id=#{id}")
	void updateTicketStatus(Ticket ticket);

	Member queryMemberByPiid(String piid);

	
	List<Map<String, Object>> queryCertByMemberid(Integer memberId);

}
