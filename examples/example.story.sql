-- Generated statements for node: D1_0
INSERT INTO storytable VALUES (1,'Example story','D1_0','You are confronted with a serious question: To cheese it or not to cheese it?',0);
INSERT INTO answers VALUES ('Example story','D1_0','A','Cheese it!');
INSERT INTO results VALUES (1,'Example story','D1_0','A',0,50,'D2_0');
INSERT INTO results VALUES (2,'Example story','D1_0','A',50,100,'D2_1');
INSERT INTO answers VALUES ('Example story','D1_0','B','Don\'t cheese it!');
INSERT INTO results VALUES (3,'Example story','D1_0','B',0,100,'D2_2');
-- Generated statements for node: D2_0
INSERT INTO storytable VALUES (2,'Example story','D2_0','You were able to cheese it!',1);
INSERT INTO answers VALUES ('Example story','D2_0','A','Proceed');
INSERT INTO results VALUES (4,'Example story','D2_0','A',0,100,'D3_0');
-- Generated statements for node: D2_1
INSERT INTO storytable VALUES (3,'Example story','D2_1','You were unsuccessful at cheesing it!',2);
INSERT INTO answers VALUES ('Example story','D2_1','A','Proceed');
INSERT INTO results VALUES (5,'Example story','D2_1','A',0,100,'D3_0');
-- Generated statements for node: D2_2
INSERT INTO storytable VALUES (4,'Example story','D2_2','You proceed not to cheese it. This is an additional line to test the parser. Yet another line!',3);
INSERT INTO answers VALUES ('Example story','D2_2','A','Proceed');
INSERT INTO results VALUES (6,'Example story','D2_2','A',0,100,'D3_0');
-- Generated statements for node: D3_0
INSERT INTO storytable VALUES (5,'Example story','D3_0','Regardless of whether you cheesed it, something happened.',4);
INSERT INTO answers VALUES ('Example story','D3_0','A','End');
INSERT INTO results VALUES (7,'Example story','D3_0','A',0,100,'END');
