/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FYPpackage.Testing_package;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 *
 * @author generals
 */
public class DummyEntity extends TwitterDataExtraction{
    
    HashMap<Long,DummyIDs> followers = new HashMap<>();
    HashMap<Long,User> longString = new HashMap<>();
    HashMap<String,User> stringLong = new HashMap<>();
    //HashMap<Long,ArrayList<Status>> userTimeline = new HashMap<>();
    HashMap<Long,ResponseList<Status>> userTimeline = new HashMap<>();
    
    public DummyEntity()
    {   
        initialise() ;//for bigclam test
  //      initialise3();//for pagerank test
    }

   /* public void initialise3()
    {
        DummyUser a_user = new DummyUser(new Long("0"),"A",3);
        DummyUser b_user = new DummyUser(new Long("1"),"B",2);
        DummyUser c_user = new DummyUser(new Long("2"),"C",5);
        DummyUser d_user = new DummyUser(new Long("3"),"D",1);
        DummyUser e_user = new DummyUser(new Long("4"),"E",2);
        DummyUser f_user = new DummyUser(new Long("5"),"F",1);
        DummyUser g_user = new DummyUser(new Long("6"),"G",2);
        DummyUser h_user = new DummyUser(new Long("7"),"H",2);
        DummyUser i_user = new DummyUser(new Long("8"),"I",1);
        DummyUser j_user = new DummyUser(new Long("9"),"J",1);
        DummyUser k_user = new DummyUser(new Long("10"),"K",1);
        DummyUser l_user = new DummyUser(new Long("11"),"L",2);
        DummyUser m_user = new DummyUser(new Long("12"),"M",1);
        DummyUser n_user = new DummyUser(new Long("13"),"N",2);
        DummyUser o_user = new DummyUser(new Long("14"),"O",1);
        DummyUser p_user = new DummyUser(new Long("15"),"P",1);
        DummyUser q_user = new DummyUser(new Long("16"),"Q",1);
        DummyUser r_user = new DummyUser(new Long("17"),"R",2);
        DummyUser s_user = new DummyUser(new Long("18"),"S",1);
        DummyUser t_user = new DummyUser(new Long("19"),"T",3);
        DummyUser u_user = new DummyUser(new Long("20"),"U",4);
        DummyUser v_user = new DummyUser(new Long("21"),"V",0);
        DummyUser w_user = new DummyUser(new Long("22"),"W",3);
        DummyUser x_user = new DummyUser(new Long("23"),"X",1);
        DummyUser za_user = new DummyUser(new Long("24"),"ZA",1);
        DummyUser zb_user = new DummyUser(new Long("25"),"ZB",3);
        DummyUser zc_user = new DummyUser(new Long("26"),"ZC",2);
        DummyUser zd_user = new DummyUser(new Long("27"),"ZD",1);
        DummyUser ze_user = new DummyUser(new Long("28"),"ZE",1);
        DummyUser zf_user = new DummyUser(new Long("29"),"ZF",0);
        DummyUser zg_user = new DummyUser(new Long("30"),"ZG",1);
        DummyUser zi_user = new DummyUser(new Long("31"),"ZI",2);
        DummyUser zj_user = new DummyUser(new Long("32"),"ZJ",4);
        DummyUser zk_user = new DummyUser(new Long("33"),"ZK",3);
        DummyUser zl_user = new DummyUser(new Long("34"),"ZL",1);
        DummyUser zm_user = new DummyUser(new Long("35"),"ZM",3);
        DummyUser zn_user = new DummyUser(new Long("36"),"ZN",2);
        DummyUser zo_user = new DummyUser(new Long("37"),"ZO",3);
        DummyUser zp_user = new DummyUser(new Long("38"),"ZP",3);
        DummyUser zq_user = new DummyUser(new Long("39"),"ZQ",1);
        DummyUser zr_user = new DummyUser(new Long("40"),"ZR",1);
        DummyUser zs_user = new DummyUser(new Long("41"),"ZS",1);
        DummyUser zt_user = new DummyUser(new Long("42"),"ZT",1);
        DummyUser zu_user = new DummyUser(new Long("43"),"ZU",1);
        DummyUser zv_user = new DummyUser(new Long("44"),"ZV",1);

        longString.put(a_user.getId(), a_user);
        longString.put(b_user.getId(), b_user);
        longString.put(c_user.getId(), c_user);
        longString.put(d_user.getId(), d_user);
        longString.put(e_user.getId(), e_user);
        longString.put(f_user.getId(), f_user);
        longString.put(g_user.getId(), g_user);
        longString.put(h_user.getId(), h_user);
        longString.put(i_user.getId(), i_user);
        longString.put(j_user.getId(), j_user);
        longString.put(k_user.getId(), k_user);
        longString.put(l_user.getId(), l_user);
        longString.put(m_user.getId(), m_user);
        longString.put(n_user.getId(), n_user);
        longString.put(o_user.getId(), o_user);
        longString.put(p_user.getId(), p_user);
        longString.put(q_user.getId(), q_user);
        longString.put(r_user.getId(), r_user);
        longString.put(s_user.getId(), s_user);
        longString.put(t_user.getId(), t_user);
        longString.put(u_user.getId(), u_user);
        longString.put(v_user.getId(), v_user);
        longString.put(w_user.getId(), w_user);
        longString.put(x_user.getId(), x_user);
        longString.put(za_user.getId(), za_user);
        longString.put(zb_user.getId(), zb_user);
        longString.put(zc_user.getId(), zc_user);
        longString.put(zd_user.getId(), zd_user);
        longString.put(ze_user.getId(), ze_user);
        longString.put(zf_user.getId(), zf_user);        
        longString.put(zg_user.getId(), zg_user);
        longString.put(zi_user.getId(), zi_user);
        longString.put(zj_user.getId(), zj_user);
        longString.put(zk_user.getId(), zk_user);
        longString.put(zl_user.getId(), zl_user);
        longString.put(zm_user.getId(), zm_user);
        longString.put(zn_user.getId(), zn_user);
        longString.put(zo_user.getId(), zo_user);
        longString.put(zp_user.getId(), zp_user);
        longString.put(zq_user.getId(), zq_user);     
        longString.put(zr_user.getId(), zr_user);
        longString.put(zs_user.getId(), zs_user);
        longString.put(zt_user.getId(), zt_user);
        longString.put(zu_user.getId(), zu_user);
        longString.put(zv_user.getId(), zv_user);

        stringLong.put(a_user.getName(), a_user);
        stringLong.put(b_user.getName(), b_user);
        stringLong.put(c_user.getName(), c_user);
        stringLong.put(d_user.getName(), d_user);
        stringLong.put(e_user.getName(), e_user);
        stringLong.put(f_user.getName(), f_user);
        stringLong.put(g_user.getName(), g_user);
        stringLong.put(h_user.getName(), h_user);
        stringLong.put(i_user.getName(), i_user);
        stringLong.put(j_user.getName(), j_user);
        stringLong.put(k_user.getName(), k_user);
        stringLong.put(l_user.getName(), l_user);
        stringLong.put(m_user.getName(), m_user);
        stringLong.put(n_user.getName(), n_user);
        stringLong.put(o_user.getName(), o_user);
        stringLong.put(p_user.getName(), p_user);
        stringLong.put(q_user.getName(), q_user);
        stringLong.put(r_user.getName(), r_user);
        stringLong.put(s_user.getName(), s_user);
        stringLong.put(t_user.getName(), t_user);
        stringLong.put(u_user.getName(), u_user);
        stringLong.put(v_user.getName(), v_user);
        stringLong.put(w_user.getName(), w_user);
        stringLong.put(x_user.getName(), x_user);
        stringLong.put(za_user.getName(), za_user);
        stringLong.put(zb_user.getName(), zb_user);
        stringLong.put(zc_user.getName(), zc_user);
        stringLong.put(zd_user.getName(), zd_user);
        stringLong.put(ze_user.getName(), ze_user);
        stringLong.put(zf_user.getName(), zf_user);        
        stringLong.put(zg_user.getName(), zg_user);
        stringLong.put(zi_user.getName(), zi_user);
        stringLong.put(zj_user.getName(), zj_user);
        stringLong.put(zk_user.getName(), zk_user);
        stringLong.put(zl_user.getName(), zl_user);
        stringLong.put(zm_user.getName(), zm_user);
        stringLong.put(zn_user.getName(), zn_user);
        stringLong.put(zo_user.getName(), zo_user);
        stringLong.put(zp_user.getName(), zp_user);
        stringLong.put(zq_user.getName(), zq_user);     
        stringLong.put(zr_user.getName(), zr_user);
        stringLong.put(zs_user.getName(), zs_user);
        stringLong.put(zt_user.getName(), zt_user);
        stringLong.put(zu_user.getName(), zu_user);
        stringLong.put(zv_user.getName(), zv_user);
    
        long ids_followers_a[] ={new Long("42")};
        long ids_followers_b[] ={}; //le compte b a comme followers a, et d qui est un compte priver;
        long ids_followers_c[] ={new Long("0"),new Long("1")};
        long ids_followers_d[] ={new Long("2"),new Long("0")};
        long ids_followers_e[] ={new Long("3"),new Long("0"),new Long("2"),new Long("5")};
        long ids_followers_f[] ={new Long("1"),new Long("2")};
        long ids_followers_g[] ={new Long("4"),new Long("2"),new Long("5")};
        long ids_followers_h[] ={new Long("37")};
        long ids_followers_i[] ={new Long("4"),new Long("38"),new Long("7")};
        long ids_followers_j[] ={new Long("8"),new Long("2"),new Long("6")};
        long ids_followers_k[] ={new Long("11"),new Long("6")};
        long ids_followers_l[] ={new Long("9")};
        long ids_followers_m[] ={new Long("10")};
        long ids_followers_n[] ={new Long("11")};
        long ids_followers_o[] ={new Long("20")};
        long ids_followers_p[] ={new Long("17")};
        long ids_followers_q[] ={new Long("15"),new Long("17"),new Long("19")};
        long ids_followers_r[] ={new Long("14"),new Long("20")};
        long ids_followers_s[] ={new Long("26"),new Long("20")};
        long ids_followers_t[] ={new Long("20"),new Long("23")};
        long ids_followers_u[] ={new Long("12")};
        long ids_followers_v[] ={new Long("39"),new Long("18"),new Long("19"),new Long("22"),new Long("24")};
        long ids_followers_w[] ={new Long("19")};
        long ids_followers_x[] ={new Long("22"),new Long("25"),new Long("13")};
        long ids_followers_za[] ={new Long("22"),new Long("26")};
        long ids_followers_zb[] ={};
        long ids_followers_zc[] ={new Long("25")};
        long ids_followers_zd[] ={new Long("25"),new Long("35")};
        long ids_followers_ze[] ={new Long("27")};
        long ids_followers_zf[] ={new Long("35"),new Long("31"),new Long("33"),new Long("30")};
        long ids_followers_zg[] ={new Long("33"),new Long("36"),new Long("38"),new Long("37")};
        long ids_followers_zi[] ={new Long("35"),new Long("32"),new Long("33"),new Long("38")};
        long ids_followers_zj[] ={new Long("36")};
        long ids_followers_zk[] ={new Long("32")};
        long ids_followers_zl[] ={new Long("32"),new Long("31")};
        long ids_followers_zm[] ={new Long("28"),new Long("34"),new Long("32")};
        long ids_followers_zn[] ={};
        long ids_followers_zo[] ={new Long("44")};
        long ids_followers_zp[] ={new Long("7"),new Long("37")};
        long ids_followers_zq[] ={new Long("40"),new Long("41")};
        long ids_followers_zr[] ={};
        long ids_followers_zs[] ={};
        long ids_followers_zt[] ={new Long("43")};
        long ids_followers_zu[] ={};
        long ids_followers_zv[] ={};

        DummyIDs d_a = new DummyIDs(ids_followers_a);
        DummyIDs d_b = new DummyIDs(ids_followers_b);
        DummyIDs d_c = new DummyIDs(ids_followers_c);
        DummyIDs d_d = new DummyIDs(ids_followers_d);
        DummyIDs d_e = new DummyIDs(ids_followers_e);
        DummyIDs d_f = new DummyIDs(ids_followers_f);
        DummyIDs d_g = new DummyIDs(ids_followers_g);
        DummyIDs d_h = new DummyIDs(ids_followers_h);
        DummyIDs d_i = new DummyIDs(ids_followers_i);
        DummyIDs d_j = new DummyIDs(ids_followers_j);
        DummyIDs d_k = new DummyIDs(ids_followers_k);
        DummyIDs d_l = new DummyIDs(ids_followers_l);
        DummyIDs d_m = new DummyIDs(ids_followers_m);
        DummyIDs d_n = new DummyIDs(ids_followers_n);
        DummyIDs d_o = new DummyIDs(ids_followers_o);
        DummyIDs d_p = new DummyIDs(ids_followers_p);
        DummyIDs d_q = new DummyIDs(ids_followers_q);
        DummyIDs d_r = new DummyIDs(ids_followers_r);
        DummyIDs d_s = new DummyIDs(ids_followers_s);
        DummyIDs d_t = new DummyIDs(ids_followers_t);
        DummyIDs d_u = new DummyIDs(ids_followers_u);
        DummyIDs d_v = new DummyIDs(ids_followers_v);
        DummyIDs d_w = new DummyIDs(ids_followers_w);
        DummyIDs d_x = new DummyIDs(ids_followers_x);
        DummyIDs d_za = new DummyIDs(ids_followers_za);
        DummyIDs d_zb = new DummyIDs(ids_followers_zb);
        DummyIDs d_zc = new DummyIDs(ids_followers_zc);
        DummyIDs d_zd = new DummyIDs(ids_followers_zd);
        DummyIDs d_ze = new DummyIDs(ids_followers_ze);
        DummyIDs d_zf = new DummyIDs(ids_followers_zf);
        DummyIDs d_zg = new DummyIDs(ids_followers_zg);
        DummyIDs d_zi = new DummyIDs(ids_followers_zi);
        DummyIDs d_zj = new DummyIDs(ids_followers_zj);
        DummyIDs d_zk = new DummyIDs(ids_followers_zk);
        DummyIDs d_zl = new DummyIDs(ids_followers_zl);
        DummyIDs d_zm = new DummyIDs(ids_followers_zm);
        DummyIDs d_zn = new DummyIDs(ids_followers_zn);
        DummyIDs d_zo = new DummyIDs(ids_followers_zo);
        DummyIDs d_zp = new DummyIDs(ids_followers_zp);
        DummyIDs d_zq = new DummyIDs(ids_followers_zq);
        DummyIDs d_zr = new DummyIDs(ids_followers_zr);
        DummyIDs d_zs = new DummyIDs(ids_followers_zs);
        DummyIDs d_zt = new DummyIDs(ids_followers_zt);
        DummyIDs d_zu = new DummyIDs(ids_followers_zu);
        DummyIDs d_zv = new DummyIDs(ids_followers_zv);
        
        followers.put(a_user.getId(), d_a);
        followers.put(b_user.getId(), d_b);
        followers.put(c_user.getId(), d_c);
        followers.put(d_user.getId(), d_d);
        followers.put(e_user.getId(), d_e);
        followers.put(f_user.getId(), d_f);
        followers.put(g_user.getId(), d_g);
        followers.put(h_user.getId(), d_h);
        followers.put(i_user.getId(), d_i);
        followers.put(j_user.getId(), d_j);
        followers.put(k_user.getId(), d_k);
        followers.put(l_user.getId(), d_l);
        followers.put(m_user.getId(), d_m);
        followers.put(n_user.getId(), d_n);
        followers.put(o_user.getId(), d_o);
        followers.put(p_user.getId(), d_p);
        followers.put(q_user.getId(), d_q);
        followers.put(r_user.getId(), d_r);
        followers.put(s_user.getId(), d_s);
        followers.put(t_user.getId(), d_t);
        followers.put(u_user.getId(), d_u);
        followers.put(v_user.getId(), d_v);
        followers.put(w_user.getId(), d_w);
        followers.put(x_user.getId(), d_x);
        followers.put(za_user.getId(), d_za);
        followers.put(zb_user.getId(), d_zb);
        followers.put(zc_user.getId(), d_zc);
        followers.put(zd_user.getId(), d_zd);
        followers.put(ze_user.getId(), d_ze);
        followers.put(zf_user.getId(), d_zf);
        followers.put(zg_user.getId(), d_zg);
        followers.put(zi_user.getId(), d_zi);
        followers.put(zj_user.getId(), d_zj);
        followers.put(zk_user.getId(), d_zk);
        followers.put(zl_user.getId(), d_zl);
        followers.put(zm_user.getId(), d_zm);
        followers.put(zn_user.getId(), d_zn);
        followers.put(zo_user.getId(), d_zo);
        followers.put(zp_user.getId(), d_zp);
        followers.put(zq_user.getId(), d_zq);
        followers.put(zr_user.getId(), d_zr);
        followers.put(zs_user.getId(), d_zs);
        followers.put(zt_user.getId(), d_zt);
        followers.put(zu_user.getId(), d_zu);
        followers.put(zv_user.getId(), d_zv);
        

    }
    */
  
    /*public void initialise2()
    {
        DummyUser a_user = new DummyUser(new Long("0"),"A",1);
        DummyUser b_user = new DummyUser(new Long("1"),"B",1);
        DummyUser c_user = new DummyUser(new Long("2"),"C",1);

        longString.put(a_user.getId(), a_user);
        longString.put(b_user.getId(), b_user);
        longString.put(c_user.getId(), c_user);        
        
        stringLong.put(a_user.getName(), a_user);
        stringLong.put(b_user.getName(), b_user);
        stringLong.put(c_user.getName(), c_user);

        long ids_followers_a[] ={new Long("2")};
        long ids_followers_b[] ={new Long("0"),new Long("3")}; //le compte b a comme followers a, et d qui est un compte priver;
        long ids_followers_c[] ={new Long("1")};
        
        DummyIDs d_a = new DummyIDs(ids_followers_a);
        DummyIDs d_b = new DummyIDs(ids_followers_b);
        DummyIDs d_c = new DummyIDs(ids_followers_c);

        followers.put(a_user.getId(), d_a);
        followers.put(b_user.getId(), d_b);
        followers.put(c_user.getId(), d_c);
    }
    */
    public void initialise()
    {
        //initialiser
        DummyUser a_user = new DummyUser(new Long("0"),"A",0,"US","EN");
        DummyUser b_user = new DummyUser(new Long("1"),"B",0,"UK","EN");
        DummyUser c_user = new DummyUser(new Long("2"),"C",1,"CHINA","CH");
        DummyUser d_user = new DummyUser(new Long("3"),"D",1,"CHINA","CH");
        DummyUser e_user = new DummyUser(new Long("4"),"E",1,"US","EN");
        DummyUser f_user = new DummyUser(new Long("5"),"F",2,"US","EN");
        DummyUser g_user = new DummyUser(new Long("6"),"G",2,"FRANCE","FR");
        DummyUser h_user = new DummyUser(new Long("7"),"H",1,"FRANCE","FR");
        DummyUser i_user = new DummyUser(new Long("8"),"I",1,"FRANCE","FR");
        DummyUser j_user = new DummyUser(new Long("9"),"J",1,"FRANCE","FR");
        
        //le compte D represente un compte priver; 
        //dnc aucune fonction est applicable sur lui;
        //DummyUser d_user = new DummyUser(new Long("3"),"D",1);
    
        longString.put(a_user.getId(), a_user);
        longString.put(b_user.getId(), b_user);
        longString.put(c_user.getId(), c_user);
        longString.put(d_user.getId(), d_user);
        longString.put(e_user.getId(), e_user);
        longString.put(f_user.getId(), f_user);
        longString.put(g_user.getId(), g_user);
        longString.put(h_user.getId(), h_user);
        longString.put(i_user.getId(), i_user);
        longString.put(j_user.getId(), j_user);
        //longString.put(d_user.getId(),d_user);
        
        stringLong.put(a_user.getName(), a_user);
        stringLong.put(b_user.getName(), b_user);
        stringLong.put(c_user.getName(), c_user);
        stringLong.put(d_user.getName(), d_user);
        stringLong.put(e_user.getName(), e_user);
        stringLong.put(f_user.getName(), f_user);
        stringLong.put(g_user.getName(), g_user);
        stringLong.put(h_user.getName(), h_user);
        stringLong.put(i_user.getName(), i_user);
        stringLong.put(j_user.getName(), j_user);
        //stringLong.put(d_user.getName(), d_user);
        
        long ids_followers_a[] ={new Long("2"),new Long("3"),new Long("4"),new Long("5"),new Long("6")};
        long ids_followers_b[] ={new Long("5"),new Long("6"),new Long("7"),new Long("8"),new Long("9")}; //le compte b a comme followers a, et d qui est un compte priver;
        long ids_followers_c[] ={};
        long ids_followers_d[] ={};
        long ids_followers_e[] ={};
        long ids_followers_f[] ={};
        long ids_followers_g[] ={};
        long ids_followers_h[] ={};
        long ids_followers_i[] ={};
        long ids_followers_j[] ={};
        //long ids_followers_d[] ={};
        
        DummyIDs d_a = new DummyIDs(ids_followers_a);
        DummyIDs d_b = new DummyIDs(ids_followers_b);
        DummyIDs d_c = new DummyIDs(ids_followers_c);
        DummyIDs d_d = new DummyIDs(ids_followers_d);
        DummyIDs d_e = new DummyIDs(ids_followers_e);
        DummyIDs d_f = new DummyIDs(ids_followers_f);
        DummyIDs d_g = new DummyIDs(ids_followers_g);
        DummyIDs d_h = new DummyIDs(ids_followers_h);
        DummyIDs d_i = new DummyIDs(ids_followers_i);
        DummyIDs d_j = new DummyIDs(ids_followers_j);
        //DummyIDs d_d = new DummyIDs(ids_followers_d);
        
        followers.put(a_user.getId(), d_a);
        followers.put(b_user.getId(), d_b);
        followers.put(c_user.getId(), d_c);
        followers.put(d_user.getId(), d_d);
        followers.put(e_user.getId(), d_e);
        followers.put(f_user.getId(), d_f);
        followers.put(g_user.getId(), d_g);
        followers.put(h_user.getId(), d_h);
        followers.put(i_user.getId(), d_i);
        followers.put(j_user.getId(), d_j);
        //followers.put(d_user.getId(),d_d);
        
        DummyStatus statusA1 = new DummyStatus("I like environnement.");
        DummyStatus statusA2 = new DummyStatus("I love environnement.");
        DummyStatus statusA3 = new DummyStatus("I like so much environnement.");
        DummyStatus statusA4 = new DummyStatus("I like a little environnement.");
        ArrayList<Status> listA = new ArrayList<>();
        listA.add(statusA1);
        listA.add(statusA2);
        listA.add(statusA3);
        listA.add(statusA4);
        DummyResponseList<Status> responseA = new DummyResponseList<>(listA);
        
        DummyStatus statusB1 = new DummyStatus("I like Lindt.");
        DummyStatus statusB2 = new DummyStatus("I love Lindt.");
        DummyStatus statusB3 = new DummyStatus("I like so much Lindt.");
        DummyStatus statusB4 = new DummyStatus("I like a little Lindt.");
        ArrayList<Status> listB = new ArrayList<>();
        listB.add(statusB1);
        listB.add(statusB2);
        listB.add(statusB3);
        listB.add(statusB4);
        DummyResponseList<Status> responseB = new DummyResponseList<>(listB);
        
        DummyStatus statusC1 = new DummyStatus("I like environnement.");
        DummyStatus statusC2 = new DummyStatus("I love environnement.");
        DummyStatus statusC3 = new DummyStatus("I like so much environnement.");
        DummyStatus statusC4 = new DummyStatus("I like a little environnement.");
        ArrayList<Status> listC = new ArrayList<>();
        listC.add(statusC1);
        listC.add(statusC2);
        listC.add(statusC3);
        listC.add(statusC4);
        DummyResponseList<Status> responseC = new DummyResponseList<>(listC);
        
        DummyStatus statusD1 = new DummyStatus("I like environnement.");
        DummyStatus statusD2 = new DummyStatus("I love environnement.");
        DummyStatus statusD3 = new DummyStatus("I like so much environnement.");
        DummyStatus statusD4 = new DummyStatus("I like a little environnement.");
        ArrayList<Status> listD = new ArrayList<>();
        listD.add(statusD1);
        listD.add(statusD2);
        listD.add(statusD3);
        listD.add(statusD4);
        DummyResponseList<Status> responseD = new DummyResponseList<>(listD);
        
        DummyStatus statusE1 = new DummyStatus("I like environnement.");
        DummyStatus statusE2 = new DummyStatus("I love environnement.");
        DummyStatus statusE3 = new DummyStatus("I like so much environnement.");
        DummyStatus statusE4 = new DummyStatus("I like a little environnement.");
        ArrayList<Status> listE = new ArrayList<>();
        listE.add(statusE1);
        listE.add(statusE2);
        listE.add(statusE3);
        listE.add(statusE4);
        DummyResponseList<Status> responseE = new DummyResponseList<>(listE);

        //COMMUN
        DummyStatus statusF1 = new DummyStatus("I like environnement.");
        DummyStatus statusF2 = new DummyStatus("I love environnement.");
        DummyStatus statusF3 = new DummyStatus("I like so much Lindt.");
        DummyStatus statusF4 = new DummyStatus("I like a little Lindt.");
        ArrayList<Status> listF = new ArrayList<>();
        listF.add(statusF1);
        listF.add(statusF2);
        listF.add(statusF3);
        listF.add(statusF4);
        DummyResponseList<Status> responseF = new DummyResponseList<>(listF);
        
        DummyStatus statusG1 = new DummyStatus("I like environnement.");
        DummyStatus statusG2 = new DummyStatus("I love environnement.");
        DummyStatus statusG3 = new DummyStatus("I like so much Lindt.");
        DummyStatus statusG4 = new DummyStatus("I like a little Lindt.");
        ArrayList<Status> listG = new ArrayList<>();
        listG.add(statusG1);
        listG.add(statusG2);
        listG.add(statusG3);
        listG.add(statusG4);
        DummyResponseList<Status> responseG = new DummyResponseList<>(listG);

        //
        
        DummyStatus statusH1 = new DummyStatus("I like Lindt.");
        DummyStatus statusH2 = new DummyStatus("I love Lindt.");
        DummyStatus statusH3 = new DummyStatus("I like so much Lindt.");
        DummyStatus statusH4 = new DummyStatus("I like a little Lindt.");
        ArrayList<Status> listH = new ArrayList<>();
        listH.add(statusH1);
        listH.add(statusH2);
        listH.add(statusH3);
        listH.add(statusH4);
        DummyResponseList<Status> responseH = new DummyResponseList<>(listH);
        
        DummyStatus statusI1 = new DummyStatus("I like Lindt.");
        DummyStatus statusI2 = new DummyStatus("I love Lindt.");
        DummyStatus statusI3 = new DummyStatus("I like so much Lindt.");
        DummyStatus statusI4 = new DummyStatus("I like a little Lindt.");
        ArrayList<Status> listI = new ArrayList<>();
        listI.add(statusI1);
        listI.add(statusI2);
        listI.add(statusI3);
        listI.add(statusI4);
        DummyResponseList<Status> responseI = new DummyResponseList<>(listI);
        
        DummyStatus statusJ1 = new DummyStatus("I like Lindt.");
        DummyStatus statusJ2 = new DummyStatus("I love Lindt.");
        DummyStatus statusJ3 = new DummyStatus("I like so much Lindt.");
        DummyStatus statusJ4 = new DummyStatus("I like a little Lindt.");
        ArrayList<Status> listJ = new ArrayList<>();
        listJ.add(statusJ1);
        listJ.add(statusJ2);
        listJ.add(statusJ3);
        listJ.add(statusJ4);
        DummyResponseList<Status> responseJ = new DummyResponseList<>(listJ);
        
        userTimeline.put(stringLong.get("A").getId(),responseA);
        userTimeline.put(stringLong.get("B").getId(),responseB);
        userTimeline.put(stringLong.get("C").getId(),responseC);
        userTimeline.put(stringLong.get("D").getId(),responseD);
        userTimeline.put(stringLong.get("E").getId(),responseE);
        userTimeline.put(stringLong.get("F").getId(),responseF);
        userTimeline.put(stringLong.get("G").getId(),responseG);
        userTimeline.put(stringLong.get("H").getId(),responseH);
        userTimeline.put(stringLong.get("I").getId(),responseI);
        userTimeline.put(stringLong.get("J").getId(),responseJ);
    }

    public static void main(String []argv) throws TwitterException
    {
        //DummyEntity  a = new DummyEntity();
        //Map<String,RateLimitStatus> map = a.getRateLimitStatus("followers");
        //RateLimitStatus s = map.get("followers/ids");
        //System.out.println(s.getRemaining());
        DummyEntity d_e = new DummyEntity();
        DummyIDs ids = (DummyIDs) d_e.getFollowersIDs(new Long("0"),new Long("0"));
        System.out.println(ids.getIDs()[0]);
        
    }
    @Override
    public Map<String, RateLimitStatus> getRateLimitStatus(String family) throws TwitterException {
        
                
            Map<String,RateLimitStatus> map = new HashMap<>();
            RateLimitStatus r = new RateLimitStatus() {

            @Override
            public int getRemaining() {
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return 15;
            }

            @Override
            public int getLimit() {
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return 15;
            }

            @Override
            public int getResetTimeInSeconds() {
              //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return 0;
            }

            @Override
            public int getSecondsUntilReset() {
              //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return 0;
            }
        };
         RateLimitStatus r2 = new RateLimitStatus() {

            @Override
            public int getRemaining() {
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return 15;
            }

            @Override
            public int getLimit() {
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return 15;
            }

            @Override
            public int getResetTimeInSeconds() {
              //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return 0;
            }

            @Override
            public int getSecondsUntilReset() {
              //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return 0;
            }
        };
         
        map.put("/followers/ids", r);
        map.put("/users/lookup",r2);
        map.put("/statuses/user_timeline",r2);
        return map;
    }

    @Override
    public IDs getFollowersIDs(Long id, Long cursor) throws TwitterException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(longString.containsKey(id))
            return followers.get(id);
        else 
            throw new TwitterException("its a private account");
    }

    @Override
    public ResponseList<User> lookupUsers(String id) throws TwitterException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        DummyResponseList<User> l = new DummyResponseList<User>();
        if(stringLong.containsKey(id))
        {
            l.add(stringLong.get(id));
            return l;
        }
        else
            throw new TwitterException("its a private account");
    }

    @Override
    public ResponseList<User> lookupUsers(Long id) throws TwitterException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        DummyResponseList<User> l = new DummyResponseList<User>();
        if(longString.containsKey(id))
        {
            l.add(longString.get(id));
            return l;
        }
        else
            throw new TwitterException("its a private account");
    }

    @Override
    public User showUser(Long id) throws TwitterException {
       if(!longString.containsKey(id))
           throw new TwitterException("its a private account");
      
       return longString.get(id);
    }

    @Override
    public void setTwitterAccount(Twitter twitter) {
        try {
            throw new Exception("not supported operation for DummyEntity");
        } catch (Exception ex) {
            Logger.getLogger(DummyEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ResponseList<Status> getUserTimeline(Long id, Paging page) throws TwitterException {
        if(longString.containsKey(id))
        {
            ResponseList<Status>  s =userTimeline.get(id);
            int p=page.getPage();//AYA PAGE;
            int c =page.getCount();//ADAI BADK TESHAB;
            //if(c>(s.size()-(p-1)*c))
              if((p-1)*c < s.size())
              {
                  int dest = 0;
                  if(((p-1)*c -1 +c )>=s.size())
                  {
                      dest = s.size();
                  }
                  else
                      dest = (p-1)*c  +c;
                  ArrayList<Status> sub = new ArrayList<Status>(s.subList((p-1)*c,dest));
                  //ArrayList<Status>) s.subList((p-1)*c, dest);
                  return new DummyResponseList( sub);
              }
              else
                  return new DummyResponseList(new ArrayList<Status>());
            //Long idl = longString.get(id).getId();
         //   return userTimeline.get(id);
        }
        else
            throw new TwitterException("it's a private account");
            
    }
    
}
