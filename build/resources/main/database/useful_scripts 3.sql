# average age of the club
SELECT
    AVG(age) as 'Average Age'
FROM
    (SELECT TIMESTAMPDIFF(YEAR, p.BIRTHDAY, NOW()) AS age
     FROM person p
              LEFT JOIN membership_id id ON p.MS_ID = id.MS_ID
     WHERE id.FISCAL_YEAR = YEAR(NOW()) AND id.RENEW = 1 AND p.IS_ACTIVE = 1) AS derived;

# sum of all ages
SELECT
    SUM(IF(age BETWEEN 0 and 10,1,0)) as '0 - 10',
    SUM(IF(age BETWEEN 11 and 20,1,0)) as '11 - 20',
    SUM(IF(age BETWEEN 21 and 30,1,0)) as '21 - 30',
    SUM(IF(age BETWEEN 31 and 40,1,0)) as '31 - 40',
    SUM(IF(age BETWEEN 41 and 50,1,0)) as '41 - 50',
    SUM(IF(age BETWEEN 51 and 60,1,0)) as '51 - 60',
    SUM(IF(age BETWEEN 61 and 70,1,0)) as '61 - 70',
    SUM(IF(age BETWEEN 71 and 80,1,0)) as '71 - 80',
    SUM(IF(age BETWEEN 81 and 90,1,0)) as '81 - 90',
    SUM(IF(age IS NULL, 1, 0)) as 'Not Reported',
    COUNT(*) AS 'Total People'
FROM
    (SELECT DATE_FORMAT(FROM_DAYS(DATEDIFF(now(), p.BIRTHDAY)), '%Y') AS age
     FROM person p
              LEFT JOIN membership_id id ON p.MS_ID = id.MS_ID
     WHERE id.FISCAL_YEAR = YEAR(now()) AND id.RENEW = 1 AND p.IS_ACTIVE = 1) AS derived;


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
     WHERE id.FISCAL_YEAR=2023 and id.RENEW=1 and p.MEMBER_TYPE = 3 and p.IS_ACTIVE=1) AS derived;


### Counts number of memberships per type for a given year

SELECT
    id.FISCAL_YEAR AS 'YEAR',
    COUNT(DISTINCT IF(id.MEM_TYPE = 'RM' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'REGULAR',
    COUNT(DISTINCT IF(id.MEM_TYPE = 'FM' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'FAMILY',
    COUNT(DISTINCT IF(id.MEM_TYPE = 'SO' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'SOCIAL',
    COUNT(DISTINCT IF(id.MEM_TYPE = 'LA' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'LAKE_ASSOCIATES',
    COUNT(DISTINCT IF(id.MEM_TYPE = 'LM' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'LIFE_MEMBERS',
    COUNT(DISTINCT IF(id.MEM_TYPE = 'SM' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'STUDENT',
    COUNT(DISTINCT IF(id.MEM_TYPE = 'RF' and id.RENEW=true,id.MEMBERSHIP_ID , NULL)) AS 'RACE_FELLOWS',
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
           and FIELD_NAME='Initiation'
           and VALUE < 1000 and VALUE > 0 and i2.COMMITTED=true


## put in msid and creates other Beach Spot Board invoice item for 1985
SET @id_value := (SELECT ID + 1 FROM invoice_item ORDER BY ID DESC LIMIT 1);
SET @msid := 391;
INSERT INTO invoice_item ()
VALUES(@id_value,
       (select ID from invoice where FISCAL_YEAR='1985' and MS_ID=@msid),
       @msid,1985,'Beach Spot Board',false,0.00,0);