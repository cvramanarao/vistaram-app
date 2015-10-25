-- MysQl dump 10.13  distrib 5.5.40, for debian-linux-gnu (x86_64)
--
-- host: localhost    database: vistaram
-- ------------------------------------------------------
-- server version	5.5.40-0ubuntu0.14.04.1-log

/*!40101 seT @old_characTer_seT_clienT=@@characTer_seT_clienT */;
/*!40101 seT @old_characTer_seT_resUlTs=@@characTer_seT_resUlTs */;
/*!40101 seT @old_collaTion_connecTion=@@collaTion_connecTion */;
/*!40101 seT naMes utf8 */;
/*!40103 seT @old_TiMe_zone=@@TiMe_zone */;
/*!40103 seT TiMe_zone='+00:00' */;
/*!40014 seT @old_UniQUe_checKs=@@UniQUe_checKs, UniQUe_checKs=0 */;
/*!40014 seT @old_foreign_KeY_checKs=@@foreign_KeY_checKs, foreign_KeY_checKs=0 */;
/*!40101 seT @old_sQl_Mode=@@sQl_Mode, sQl_Mode='no_aUTo_valUe_on_zero' */;
/*!40111 seT @old_sQl_noTes=@@sQl_noTes, sQl_noTes=0 */;

--
-- Table structure for table bank_details
--

drop Table if eXisTs bank_details;
/*!40101 seT @saved_cs_client     = @@character_set_client */;
/*!40101 seT character_set_client = utf8 */;
CREATE Table bank_details (
  id int(10) noT nUll aUTo_increMenT,
  bank_name varchar(150) collaTe utf8_unicode_ci noT nUll,
  account_name varchar(120) collaTe utf8_unicode_ci noT nUll,
  account_number int(20) noT nUll,
  ifsc_code varchar(11) collaTe utf8_unicode_ci noT nUll,
  guest_details_id int(11) noT nUll,
  priMarY KeY (id),
  KeY guest_details_id_fk (guest_details_id),
  consTrainT guest_details_id_fk foreign KeY (guest_details_id) references guest_details (id) on deleTe cascade on UpdaTe cascade
) engine=MyisaM aUTo_increMenT=4 defaUlT charseT=utf8 collaTe=utf8_unicode_ci;
/*!40101 seT character_set_client = @saved_cs_client */;

--
-- dumping data for table bank_details
--

locK Tables bank_details WriTe;
/*!40000 alTer Table bank_details disable KeYs */;
inserT inTo bank_details valUes (1,'icici','Willian adam',222114482,'icic0001144',1),(2,'hdfc','Jessy alwa',2147483647,'hdfc9908876',2),(3,'idbi','Wilson James',2147483647,'idbi5559966',3);
/*!40000 alTer Table bank_details enable KeYs */;
UnlocK Tables;

--
-- Table structure for table booking_details
--

drop Table if eXisTs booking_details;
/*!40101 seT @saved_cs_client     = @@character_set_client */;
/*!40101 seT character_set_client = utf8 */;
CREATE Table booking_details (
  id int(10) noT nUll aUTo_increMenT,
  booking_agent varchar(120) collaTe utf8_unicode_ci noT nUll,
  voucher_id varchar(30) collaTe utf8_unicode_ci noT nUll,
  booking_date date noT nUll,
  checking_date date noT nUll,
  checkout_date date noT nUll,
  no_of_nights_stayed int(4) noT nUll,
  number_of_rooms int(4) noT nUll defaUlT '1',
  guest_id int(10) noT nUll,
  hotel_details_id int(11) ,
  properties_id int(11) ,
  rate_plan_id int(11) ,
  room_details_id int(11) ,
  comments_or_requests blob ,
  priMarY KeY (id),
  KeY hotel_details_id_fk (hotel_details_id),
  KeY inclusions_properties_id_fk (properties_id),
  KeY rate_plan_id_fk (rate_plan_id),
  KeY room_details_id_fk (room_details_id),
  consTrainT hotel_details_id_fk foreign KeY (hotel_details_id) references hotel_details (id) on deleTe cascade on UpdaTe cascade,
  consTrainT inclusions_properties_id_fk foreign KeY (properties_id) references inclusions (id) on deleTe cascade on UpdaTe cascade,
  consTrainT rate_plan_id_fk foreign KeY (rate_plan_id) references rate_plan (id) on deleTe cascade on UpdaTe cascade,
  consTrainT room_details_id_fk foreign KeY (room_details_id) references room_details (id) on deleTe cascade on UpdaTe cascade
) engine=MyisaM defaUlT charseT=utf8 collaTe=utf8_unicode_ci;
/*!40101 seT character_set_client = @saved_cs_client */;

--
-- dumping data for table booking_details
--

locK Tables booking_details WriTe;
/*!40000 alTer Table booking_details disable KeYs */;
/*!40000 alTer Table booking_details enable KeYs */;
UnlocK Tables;

--
-- Table structure for table hotel_details
--

drop Table if eXisTs hotel_details;
/*!40101 seT @saved_cs_client     = @@character_set_client */;
/*!40101 seT character_set_client = utf8 */;
CREATE Table hotel_details (
  id int(10) noT nUll aUTo_increMenT,
  hotel_name varchar(120) collaTe utf8_unicode_ci noT nUll,
  place varchar(80) collaTe utf8_unicode_ci noT nUll,
  country varchar(55) collaTe utf8_unicode_ci noT nUll,
  zip_code int(20) noT nUll,
  contact_info int(10) noT nUll,
  Mail_id varchar(150) collaTe utf8_unicode_ci noT nUll,
  map varchar(255) collaTe utf8_unicode_ci noT nUll,
  priMarY KeY (id)
) engine=MyisaM defaUlT charseT=utf8 collaTe=utf8_unicode_ci;
/*!40101 seT character_set_client = @saved_cs_client */;

--
-- dumping data for table hotel_details
--

locK Tables hotel_details WriTe;
/*!40000 alTer Table hotel_details disable KeYs */;
/*!40000 alTer Table hotel_details enable KeYs */;
UnlocK Tables;

--
-- Table structure for table inclusions
--

drop Table if eXisTs inclusions;
/*!40101 seT @saved_cs_client     = @@character_set_client */;
/*!40101 seT character_set_client = utf8 */;
CREATE Table inclusions (
  id int(10) noT nUll aUTo_increMenT,
  property_name varchar(50) collaTe utf8_unicode_ci noT nUll,
  priMarY KeY (id)
) engine=MyisaM defaUlT charseT=utf8 collaTe=utf8_unicode_ci;
/*!40101 seT character_set_client = @saved_cs_client */;

--
-- dumping data for table inclusions
--

locK Tables inclusions WriTe;
/*!40000 alTer Table inclusions disable KeYs */;
/*!40000 alTer Table inclusions enable KeYs */;
UnlocK Tables;

--
-- Table structure for table rate_plan
--

drop Table if eXisTs rate_plan;
/*!40101 seT @saved_cs_client     = @@character_set_client */;
/*!40101 seT character_set_client = utf8 */;
CREATE Table rate_plan (
  id int(3) noT nUll aUTo_increMenT,
  rate_plan_name varchar(50) collaTe utf8_unicode_ci noT nUll,
  priMarY KeY (id)
) engine=MyisaM defaUlT charseT=utf8 collaTe=utf8_unicode_ci;
/*!40101 seT character_set_client = @saved_cs_client */;

--
-- dumping data for table rate_plan
--

locK Tables rate_plan WriTe;
/*!40000 alTer Table rate_plan disable KeYs */;
/*!40000 alTer Table rate_plan enable KeYs */;
UnlocK Tables;

--
-- Table structure for table room_details
--

drop Table if eXisTs room_details;
/*!40101 seT @saved_cs_client     = @@character_set_client */;
/*!40101 seT character_set_client = utf8 */;
CREATE Table room_details (
  id int(10) noT nUll aUTo_increMenT,
  room_name varchar(50) collaTe utf8_unicode_ci noT nUll,
  room_Type varchar(30) collaTe utf8_unicode_ci noT nUll,
  room_rate varchar(20) collaTe utf8_unicode_ci noT nUll,
  priMarY KeY (id)
) engine=MyisaM defaUlT charseT=utf8 collaTe=utf8_unicode_ci;
/*!40101 seT character_set_client = @saved_cs_client */;

--
-- dumping data for table room_details
--

locK Tables room_details WriTe;
/*!40000 alTer Table room_details disable KeYs */;
/*!40000 alTer Table room_details enable KeYs */;
UnlocK Tables;

--
-- Table structure for table guest_booking_properties
--

drop Table if eXisTs guest_booking_properties;
/*!40101 seT @saved_cs_client     = @@character_set_client */;
/*!40101 seT character_set_client = utf8 */;
CREATE Table guest_booking_properties (
  property_id int(11) noT nUll,
  booking_id int(11) noT nUll
) engine=MyisaM defaUlT charseT=latin1;
/*!40101 seT character_set_client = @saved_cs_client */;

--
-- dumping data for table guest_booking_properties
--

locK Tables guest_booking_properties WriTe;
/*!40000 alTer Table guest_booking_properties disable KeYs */;
/*!40000 alTer Table guest_booking_properties enable KeYs */;
UnlocK Tables;

--
-- Table structure for table guest_details
--

drop Table if eXisTs guest_details;
/*!40101 seT @saved_cs_client     = @@character_set_client */;
/*!40101 seT character_set_client = utf8 */;
CREATE Table guest_details (
  id int(10) noT nUll aUTo_increMenT,
  first_name varchar(30) collaTe utf8_unicode_ci noT nUll,
  last_name varchar(40) collaTe utf8_unicode_ci noT nUll,
  dob date ,
  contact_num int(11) ,
  email_id varchar(200) collaTe utf8_unicode_ci,
  gender char(1) collaTe utf8_unicode_ci,
  occupation varchar(30) collaTe utf8_unicode_ci,
  zip_code int(15),
  address varchar(250) collaTe utf8_unicode_ci,
  priMarY KeY (id) 
) engine=MyisaM aUTo_increMenT=4 defaUlT charseT=utf8 collaTe=utf8_unicode_ci;
/*!40101 seT character_set_client = @saved_cs_client */;

--
-- dumping data for table guest_details
--

locK Tables guest_details WriTe;
/*!40000 alTer Table guest_details disable KeYs */;
inserT inTo guest_details valUes (1,'Willian','adam','1965-12-12',993882774,'willian.adam@gmail.com','M','dev',349822,'sydney,australia'),(2,'alwa','Jessy','1962-03-10',2147483647,'alwa_jessy@yahoomail.com','f','hr Manager',2251,'nYc,Usa'),(3,'Wilson','James','1970-05-11',12293445,'wilsonjames@hotmail.com','M','ceo',98217,'london,UK');
/*!40000 alTer Table guest_details enable KeYs */;
UnlocK Tables;
/*!40103 seT TiMe_zone=@old_TiMe_zone */;

/*!40101 seT sQl_Mode=@old_sQl_Mode */;
/*!40014 seT foreign_KeY_checKs=@old_foreign_KeY_checKs */;
/*!40014 seT UniQUe_checKs=@old_UniQUe_checKs */;
/*!40101 seT characTer_seT_clienT=@old_characTer_seT_clienT */;
/*!40101 seT characTer_seT_resUlTs=@old_characTer_seT_resUlTs */;
/*!40101 seT collaTion_connecTion=@old_collaTion_connecTion */;
/*!40111 seT sQl_noTes=@old_sQl_noTes */;

-- dump completed on 2015-10-18 18:38:17
