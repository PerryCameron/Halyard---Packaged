package com.ecsail;

import com.ecsail.enums.MembershipType;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.select.SqlEmail;
import com.ecsail.sql.select.SqlMoney;
import com.ecsail.sql.select.SqlPerson;
import com.ecsail.sql.select.SqlPhone;
import com.ecsail.structures.MembershipListDTO;
import com.ecsail.structures.MoneyDTO;
import com.ecsail.structures.PersonDTO;
import okhttp3.HttpUrl;


public class EmailLinkBuilder {


    final String url = "https://form.jotform.com/";
    final String formId = "213175957272058";


    public EmailLinkBuilder() {
    }

    public String createLinkData(MembershipListDTO ml, String selectedYear)  {

        PersonDTO primaryMember = null;
        PersonDTO secondaryMember = null;
        MoneyDTO mo = SqlMoney.getMoneyRecordByMsidAndYear(ml.getMsid(),selectedYear);
        System.out.println("Using money record from " + mo.getFiscal_year());
        System.out.println("Work Credits=" + mo.getWork_credit());

        HttpUrl.Builder queryUrlBuilder = HttpUrl.get(url + formId).newBuilder();
        // Membership ID
        queryUrlBuilder.addQueryParameter("memid", String.valueOf(ml.getMembershipId()));
        // Membership Type
        queryUrlBuilder.addQueryParameter("membershipType",String.valueOf(MembershipType.getByCode(ml.getMemType())));
        // Address line 1
        queryUrlBuilder.addQueryParameter("address[addr_line1]", ml.getAddress());
        // City
        queryUrlBuilder.addQueryParameter("address[city]",ml.getCity());
        // State
        queryUrlBuilder.addQueryParameter("address[state]",ml.getState());
        // Zip
        queryUrlBuilder.addQueryParameter("address[postal]",ml.getZip());
        // Work Credits
        queryUrlBuilder.addQueryParameter("workCredit", String.valueOf(mo.getWork_credit()));
        // Winter Storage
        queryUrlBuilder.addQueryParameter("winterStorage", String.valueOf(mo.getWinter_storage()));
        // get all information related to primary member
        queryUrlBuilder.addQueryParameter("otherFee", String.valueOf(mo.getOther()));
        if(SqlExists.personExistsByType(String.valueOf(ml.getMsid()),"1")) {
            primaryMember = SqlPerson.getPerson(ml.getMsid(), 1);
            queryUrlBuilder.addQueryParameter("primaryMember[first]",primaryMember.getFname());

            queryUrlBuilder.addQueryParameter("primaryMember[last]",primaryMember.getLname());

            queryUrlBuilder.addQueryParameter("primaryOccupation",primaryMember.getOccupation());

            queryUrlBuilder.addQueryParameter("primaryCompany",primaryMember.getBuisness());

            if(SqlExists.emailExists(primaryMember))
                queryUrlBuilder.addQueryParameter("primaryemail",SqlEmail.getEmail(primaryMember));

            if(SqlExists.phoneOfTypeExists(String.valueOf(primaryMember.getP_id()),"C"))
                queryUrlBuilder.addQueryParameter("primaryPhone",SqlPhone.getPhoneByType(String.valueOf(primaryMember.getP_id()),"C"));

            if(SqlExists.phoneOfTypeExists(String.valueOf(primaryMember.getP_id()),"E")) {
                queryUrlBuilder.addQueryParameter("emergencyPhone", SqlPhone.getPhoneByType(String.valueOf(primaryMember.getP_id()), "E"));
            }
        }

        // get all information related to secondary member
        if(SqlExists.personExistsByType(String.valueOf(ml.getMsid()),"2")) {
            secondaryMember = SqlPerson.getPerson(ml.getMsid(), 2);
            queryUrlBuilder.addQueryParameter("haveSpouse","Yes");

            queryUrlBuilder.addQueryParameter("spouseName[first]",secondaryMember.getFname());

            queryUrlBuilder.addQueryParameter("spouseName[last]",secondaryMember.getLname());

            queryUrlBuilder.addQueryParameter("spouseOccupation",secondaryMember.getOccupation());

            queryUrlBuilder.addQueryParameter("spouseCompany",secondaryMember.getBuisness());

            if(SqlExists.emailExists(secondaryMember))
                queryUrlBuilder.addQueryParameter("spouseEmail",SqlEmail.getEmail(secondaryMember));

            if(SqlExists.phoneOfTypeExists(String.valueOf(secondaryMember.getP_id()),"C"))
                queryUrlBuilder.addQueryParameter("spousePhone",SqlPhone.getPhoneByType(String.valueOf(secondaryMember.getP_id()),"C"));
        }

        return queryUrlBuilder.toString();
    }

}
