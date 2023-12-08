# For setting up fees each years these need to be made

INSERT INTO db_invoice () VALUES(814, 2024,"Dues","text-field",65.0,14,false,false,false,1,true,false);
INSERT INTO db_invoice () VALUES(815, 2024,"Beach Spot","spinner",65.0,13,true,false,false,5,false,false);
INSERT INTO db_invoice () VALUES(816, 2024,"Sail Loft","spinner",65.0,12,true,false,false,1,false,false);
INSERT INTO db_invoice () VALUES(817, 2024,"Wet Slip","spinner",65.0,11,true,true,false,1,false,false);
INSERT INTO db_invoice () VALUES(818, 2024,"Winter Storage","spinner",65.0,10,true,false,false,5,false,false);
INSERT INTO db_invoice () VALUES(819, 2024,"YSP Donation","text-field",65.0,6,false,false,false,0,false,false);
INSERT INTO db_invoice () VALUES(820, 2024,"Initiation","text-field",65.0,5,false,false,false,0,false,false);
INSERT INTO db_invoice () VALUES(821, 2024,"Other Fee","text-field",65.0,4,false,false,false,0,false,false);
INSERT INTO db_invoice () VALUES(822, 2024,"Work Credits","combo-box",65.0,3,true,false,true,100,false,false);
INSERT INTO db_invoice () VALUES(823, 2024,"Other Credit","text-field",65.0,2,false,false,true,0,false,false);
INSERT INTO db_invoice () VALUES(824, 2024,"Position Credit","none",65.0,1,false,false,true,0,false,false);
INSERT INTO db_invoice () VALUES(825, 2024,"Summer Storage","itemized",65.0,8,true,false,false,5,false,true);
INSERT INTO db_invoice () VALUES(826, 2024,"Keys","itemized",65.0,7,true,false,false,5,false,true);
INSERT INTO db_invoice () VALUES(827, 2024,"Kayak","itemized",65.0,9,true,false,false,10,false,true);

INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Dues",475.00,814,2024,"Regular",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Dues",475.00,814,2024,"Family",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Dues",90.00,814,2024,"Lake Associate",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Dues",125.00,814,2024,"Social",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Beach Spot",50.00,815,2024,"Beach Spot Fee",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Sail Loft",15.00,816,2024,"Sail Loft Access Fee",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Wet Slip",450.00,817,2024,"Wet Slip Fee",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Winter Storage",125.00,818,2024,"Winter Storage Fee",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Initiation",1500.00,820,2024,"Initiation Fee",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Work Credits",15.00,822,2024,"Work Credits",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Summer Storage",200.00,825,2024,"Beam Over 5 foot",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Summer Storage",50.00,825,2024,"Beam Under 5 foot",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Keys",10.00,826,2024,"Gate Key",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Keys",10.00,826,2024,"Sail Loft Key",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Keys",10.00,826,2024,"Kayak Shed Key",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Kayak",25.00,827,2024,"Kayak Rack",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Kayak",35.00,827,2024,"Kayak Beach Rack",false);
INSERT INTO fee (FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, Description, DEFAULT_FEE) VALUES("Kayak",100.00,827,2024,"Kayak Shed",false);