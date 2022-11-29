# sum of all ages
SELECT
    SUM(IF(age BETWEEN 0 and 9,1,0)) as '0 - 9',
    SUM(IF(age BETWEEN 10 and 19,1,0)) as '10 - 19',
    SUM(IF(age BETWEEN 20 and 29,1,0)) as '20 - 29',
    SUM(IF(age BETWEEN 30 and 39,1,0)) as '30 - 39',
    SUM(IF(age BETWEEN 40 and 49,1,0)) as '40 - 49',
    SUM(IF(age BETWEEN 50 and 59,1,0)) as '50 - 59',
    SUM(IF(age BETWEEN 60 and 69,1,0)) as '60 - 69',
    SUM(IF(age BETWEEN 70 and 79,1,0)) as '70 - 79',
    SUM(IF(age >=80, 1, 0)) as 'Over 80',
    SUM(IF(age IS NULL, 1, 0)) as 'Not Reported'
FROM
    (select DATE_FORMAT(FROM_DAYS(DATEDIFF(now(),(p.BIRTHDAY))), '%Y') AS age
     from person p
              left join membership_id id on p.MS_ID=id.MS_ID
     WHERE id.FISCAL_YEAR=2022 and id.RENEW=1 and p.IS_ACTIVE=1) AS derived;

#sum of children
SELECT

    SUM(IF(age BETWEEN 0 and 5,1,0)) as '0 - 5',
    SUM(IF(age BETWEEN 6 and 10,1,0)) as '6 - 10',
    SUM(IF(age BETWEEN 11 and 15,1,0)) as '11 - 15',
    SUM(IF(age BETWEEN 16 and 20,1,0)) as '16 - 20',
    SUM(IF(age BETWEEN 21 and 25,1,0)) as '21 - 25',
    SUM(IF(age BETWEEN 26 and 30,1,0)) as '26 - 30',
    SUM(IF(age IS NULL, 1, 0)) as 'Not Reported'
FROM
    (select DATE_FORMAT(FROM_DAYS(DATEDIFF(now(),(p.BIRTHDAY))), '%Y') AS age
     from person p
              left join membership_id id on p.MS_ID=id.MS_ID
     WHERE id.FISCAL_YEAR=2022 and id.RENEW=1 and p.MEMBER_TYPE = 3 and p.IS_ACTIVE=1) AS derived;


### Counts number of memberships per type for a given year

SELECT
    id.FISCAL_YEAR AS 'YEAR',
    COUNT(DISTINCT IF(id.MEM_TYPE = 'RM' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'REGULAR',
    COUNT(DISTINCT IF(id.MEM_TYPE = 'FM' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'FAMILY',
    COUNT(DISTINCT IF(id.MEM_TYPE = 'SO' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'SOCIAL',
    COUNT(DISTINCT IF(id.MEM_TYPE = 'LA' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'LAKEASSOCIATES',
    COUNT(DISTINCT IF(id.MEM_TYPE = 'LM' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'LIFEMEMBERS',
    COUNT(DISTINCT IF(id.MEM_TYPE = 'SM' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'STUDENT',
    COUNT(DISTINCT IF(id.MEM_TYPE = 'RF' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'RACEFELLOWS',
    COUNT(DISTINCT IF(YEAR(m.JOIN_DATE)='2021',id.MEMBERSHIP_ID, NULL)) AS 'NEW_MEMBERS',
    COUNT(DISTINCT IF(id.MEMBERSHIP_ID >
                      (
                          select MEMBERSHIP_ID
                          from membership_id
                          where FISCAL_YEAR=2022 and MS_ID=
                                                     (
                                                         select MS_ID
                                                         from membership_id
                                                         where MEMBERSHIP_ID=
                                                               (
                                                                   select max(membership_id)
                                                                   from membership_id
                                                                   where FISCAL_YEAR=(2021)
                                                                     and membership_id < 500
                                                                     and renew=1
                                                               )
                                                           and FISCAL_YEAR=(2021)
                                                     )
                      )
                          and id.MEMBERSHIP_ID < 500
                          and YEAR(m.JOIN_DATE)!='2022'
                          and (SELECT NOT EXISTS(select mid from membership_id where FISCAL_YEAR=(2021) and RENEW=1 and MS_ID=id.MS_ID)), id.MEMBERSHIP_ID, NULL)) AS 'RETURN_MEMBERS',
    SUM(NOT RENEW) as 'NON_RENEW',
    SUM(RENEW) as 'ACTIVE_MEMBERSHIPS'
FROM membership_id id
         LEFT JOIN membership m on id.MS_ID=m.MS_ID
WHERE FISCAL_YEAR=2022;

# gets under 30 new members
select i.VALUE from invoice_item i
         left join invoice i2 on i2.ID = i.INVOICE_ID
         where i.FISCAL_YEAR=2016 and i2.FISCAL_YEAR=2016.
           and ITEM_TYPE='Initiation'
           and VALUE < 1000 and VALUE > 0 and i2.COMMITTED=true