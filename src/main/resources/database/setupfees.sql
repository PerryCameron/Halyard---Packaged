# For setting up fees each years these need to be made

INSERT INTO db_invoice () VALUES(828, 2025,"Dues","text-field",65.0,14,false,false,false,1,true,false);
INSERT INTO db_invoice () VALUES(829, 2025,"Beach Spot","spinner",65.0,13,true,false,false,5,false,false);
INSERT INTO db_invoice () VALUES(830, 2025,"Sail Loft","spinner",65.0,12,true,false,false,1,false,false);
INSERT INTO db_invoice () VALUES(831, 2025,"Wet Slip","spinner",65.0,11,true,true,false,1,false,false);
INSERT INTO db_invoice () VALUES(832, 2025,"Winter Storage","spinner",65.0,10,true,false,false,5,false,false);
INSERT INTO db_invoice () VALUES(833, 2025,"YSP Donation","text-field",65.0,6,false,false,false,0,false,false);
INSERT INTO db_invoice () VALUES(834, 2025,"Initiation","text-field",65.0,5,false,false,false,0,false,false);
INSERT INTO db_invoice () VALUES(835, 2025,"Other Fee","text-field",65.0,4,false,false,false,0,false,false);
INSERT INTO db_invoice () VALUES(836, 2025,"Work Credits","combo-box",65.0,3,true,false,true,100,false,false);
INSERT INTO db_invoice () VALUES(837, 2025,"Other Credit","text-field",65.0,2,false,false,true,0,false,false);
INSERT INTO db_invoice () VALUES(838, 2025,"Position Credit","none",65.0,1,false,false,true,0,false,false);
INSERT INTO db_invoice () VALUES(839, 2025,"Summer Storage","itemized",65.0,8,true,false,false,5,false,true);
INSERT INTO db_invoice () VALUES(840, 2025,"Keys","itemized",65.0,7,true,false,false,5,false,true);
INSERT INTO db_invoice () VALUES(841, 2025,"Kayak","itemized",65.0,9,true,false,false,10,false,true);

INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Dues",500.00,828,2025,"Regular",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Dues",500.00,828,2025,"Family",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Dues",100.00,828,2025,"Lake Associate",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Dues",150.00,828,2025,"Social",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Beach Spot",55.00,829,2025,"Beach Spot Fee",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Sail Loft",15.00,830,2025,"Sail Loft Access Fee",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Wet Slip",600.00,831,2025,"Wet Slip Fee",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Winter Storage",150.00,832,2025,"Winter Storage Fee",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Initiation",1500.00,834,2025,"Initiation Fee",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Work Credits",15.00,836,2025,"Work Credits",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Summer Storage",210.00,839,2025,"Beam Over 5 foot",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Summer Storage",55.00,839,2025,"Beam Under 5 foot",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Keys",10.00,840,2025,"Gate Key",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Keys",10.00,840,2025,"Sail Loft Key",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Keys",10.00,840,2025,"Kayak Shed Key",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Kayak",30.00,841,2025,"Kayak Rack",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Kayak",40.00,841,2025,"Kayak Beach Rack",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Kayak",100.00,841,2025,"Kayak Shed",false);