package es.caib.pagosMOCK.persistence.util;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase para generar identificadores de persistencia
 *
 */
public class GeneradorId {

	private static String [] letras = {
			"T8","16","IB","P7","U4","WQ","SI","HW","FO","MW","IV","2I","SJ","ZK","XC","U2","M4","SF","2J","SW",
			"M2","V3","LK","ZP","0C","DF","G1","TJ","CB","QN","HI","GP","VZ","MN","ZI","LJ","0D","SV","J2","DO",
			"BW","VP","LP","NO","07","WP","J8","OT","V7","J7","UJ","KZ","04","UH","TX","M0","NL","BE","XR","NH",
			"NI","C4","RE","0N","WV","NY","NV","RD","PC","JE","JK","AZ","PS","N0","TY","H2","F5","YR","I9","RZ",
			"PR","MP","KX","AK","S4","LH","IC","JV","OY","WT","GT","WM","BV","SO","NP","FE","E2","CO","B7","US",
			"CE","VA","YN","2Q","2C","JQ","P1","D7","QE","L4","B9","A0","D4","IT","IR","HL","JU","MR","TK","UC",
			"CT","DE","KU","DA","KI","12","IS","NG","HK","E3","CF","2B","1A","Q1","Z4","JG","A6","AU","H9","ZW",
			"V4","PN","IJ","K7","10","OA","F2","CK","LY","A9","HU","L2","TV","UI","VD","IE","WI","ZH","WS","EG",
			"ZD","KW","EN","OV","YI","V9","PV","QL","UP","HC","QZ","X0","RF","CN","W6","T0","XA","BD","D2","K9",
			"M1","UY","V6","HQ","FM","XY","UN","VK","RC","ZR","Q2","XH","RS","1X","UK","I8","0U","PI","U5","N3",
			"NR","R2","Y9","J0","MT","LU","EA","KL","V0","2K","G5","GJ","MZ","UF","1M","WR","H4","G4","EI","S9",
			"PF","OQ","Q4","RK","IZ","VQ","JW","GM","I3","H1","HB","N6","SB","FB","L9","QT","AV","FJ","IK","VE",
			"QM","HD","Q3","W2","1G","QW","H6","Z9","VF","DU","YD","SD","LZ","BC","UG","2E","JB","W1","MO","N4",
			"VJ","JT","RT","KP","FX","I5","RG","DL","AO","V5","VB","F9","EJ","TS","05","MI","CL","NW","JH","14",
			"O1","Y4","NJ","WK","KA","HM","AY","2F","AF","UM","BZ","Q5","0H","DB","A1","0A","YK","ST","VH","V8",
			"RJ","VY","YT","IN","ZS","OH","YZ","KG","NQ","Z5","BF","M5","ZG","D9","IM","O3","M9","N2","B5","KQ",
			"TB","AX","GW","NS","GN","TH","F7","HR","E7","HO","I1","AN","V1","0F","O6","TP","DT","FT","B8","KS",
			"Q6","KJ","AM","Y6","JX","DV","RW","X5","MC","JZ","DJ","CG","TQ","B4","RL","UA","A4","Y1","FK","S8",
			"K2","MY","0J","B2","QA","XT","1Z","LR","OC","JR","EB","QG","2M","O7","QU","H0","TZ","AJ","PZ","U9",
			"DX","CJ","EO","AW","IX","QB","Y8","E4","EV","ED","AR","T3","A2","IY","QR","VT","U6","C8","NC","ZQ",
			"BR","SE","O2","GQ","YG","0G","PB","PT","01","N9","I0","SU","RN","Q9","K0","HT","CQ","IO","P2","X2",
			"LE","0L","2H","RV","MV","XJ","PJ","EF","FY","SY","XZ","PY","JD","AH","TR","ZC","OD","AP","A8","A5",
			"E8","03","BJ","IA","PW","LN","OJ","C6","H5","YM","P3","TU","HP","NF","MH","K4","L8","AL","FI","U3",
			"Q7","XU","1R","X7","DC","BM","BO","HS","OP","XV","1K","ZT","YH","S6","QO","KM","OS","M8","WA","08",
			"HZ","DP","W3","EK","T5","AS","CD","UD","WO","HN","O8","I7","NE","XD","ZL","QK","V2","P9","YA","JF",
			"W8","DQ","TI","1O","OZ","BA","M7","SQ","S1","E1","GO","E6","F3","DZ","VO","E5","XE","L1","Z1","PK",
			"LV","R4","PG","G6","ES","FS","B6","S0","0P","C0","VG","EX","0S","BX","ZV","CV","W9","X8","PA","UX",
			"YF","KV","WU","BN","RA","XI","H8","PE","UV","JI","RQ","EL","A3","F8","XF","15","1Y","A7","AE","YP",
			"EM","J3","LA","1F","AT","OF","AC","RB","HF","L0","Y3","D8","H7","LX","R6","MD","0R","BS","XG","P0",
			"FR","G3","WX","Z7","S2","ND","FH","J5","HE","FA","R1","GX","X3","QV","NX","T2","XM","D5","EP","UL",
			"R8","C3","OG","G7","OR","I6","DM","X1","Z3","LT","NK","MB","C7","XL","C1","BQ","DW","TO","Z6","W5",
			"1S","1J","QI","FQ","GB","BU","FU","CH","OL","U8","WH","WC","PQ","UB","ON","WB","1B","S5","ML","YX",
			"0W","KY","R0","K8","SA","AQ","E0","13","RY","1T","U7","Z8","B3","ZE","EC","SL","TA","QP","OI","IQ",
			"OE","FC","FG","WJ","LG","LB","DR","CM","VR","Q8","BP","O9","B0","P4","JO","GA","QD","CP","S3","W4",
			"GS","YB","JC","H3","19","WG","OM","YV","SC","ZM","VS","SG","BT","LI","YL","KN","PX","D1","CA","ZY",
			"MJ","1E","C9","MA","L3","Y2","P6","IG","UZ","IP","U1","C2","Z2","ZN","VW","NT","CX","G8","JL","TW",
			"JY","LW","CY","06","K5","MU","P5","JP","J4","J9","UQ","2R","CW","1I","WE","PU","T4","KB","PD","I2",
			"DI","EH","BK","GR","GI","EQ","09","GL","2A","D6","1C","KD","VN","T6","HJ","2P","I4","CS","BI","QC",
			"FL","X9","PO","Y0","XO","TF","JN","MG","SR","YW","0E","L5","WY","1Q","2O","1W","YS","X6","G2","T1",
			"0K","MF","EZ","O5","OK","N5","XN","BG","RI","Y5","G0","EY","0Q","ZX","1U","HV","QS","CU","18","SM",
			"WN","EW","QF","ER","NA","KR","1V","XW","MK","YC","KE","UR","VL","CI","IH","FP","TL","TM","C5","VC",
			"G9","S7","FZ","OW","EU","QX","YQ","17","ME","GZ","N1","IU","IW","X4","MQ","VI","KC","CR","AB","YJ",
			"WD","XS","WL","UE","K6","HA","XK","E9","M3","R3","F4","JA","FW","DS","LQ","ZU","D0","DK","TN","UW",
			"FN","SK","MX","LC","L7","T9","R9","PH","K3","L6","ZO","PL","AI","1N","RU","GK","P8","UT","KH","F1",
			"JM","0O","FD","QJ","Y7","K1","RM","GE","U0","GV","VM","HG","2N","LD","DY","1P","PM","HX","ZF","O4",
			"GD","BL","F0","TE","R5","TD","ID","QY","FV","NB","NU","0T","SX","RO","2L","LS","OU","GF","IF","WF",
			"0I","0Y","BH","98","IL","ZA","0M","2D","MS","02","NZ","ET","QH","CZ","Q0","VU","LF","AG","1H","2S",
			"0B","KF","KO","LM","WZ","LO","O0","RH","GH","XB","0X","DH","1D","YU","D3","R7","UO","W7","ZB","SN",
			"DG","KT","N8","1L","GC","SH","NM","YE","OB","T7","F6","J6","JS","RX","W0","XQ","M6","N7","HY","B1",
			"YO","Z0","GY","TG","ZJ","0V","SZ","VX","2G","RP","BY","AD","TC","XP","SP","GU","0Z","DN","J1","OX"};
	
	
	/**
	 * Genera identificador unico
	 * @return Id
	 */
	public static String generarId(){	
		
		StringBuffer s = new StringBuffer();
		
		// Digitos 1-6: Año-mes-dia
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
		s.append(sdf.format(new Date()));
				
		// Digitos 7-28: System.currentTimeMillis
		String time = Long.toString(System.currentTimeMillis());
		if (time.length() < 22) {
			for (int i=time.length();i<22;i++){
				time += "0";
			}			
		}else{
			time = time.substring(time.length() - 22);
		}
		s.append(time);
		
		// Digitos 28-36: Random 
		SecureRandom sr = new SecureRandom();
		String rn = "" + sr.nextInt(99999999);
		if (rn.length() < 8) {
			for (int i=rn.length();i<8;i++){
				rn += "0";
			}			
		}
		s.append(rn);
			
		// Convertimos a tabla de carácteres (pasamos de 36 caracteres a 24)
		String gen = s.toString();
		StringBuffer s2 = new StringBuffer();
		String rp;
		for (int i=0;i<gen.length();i=i+3){			
			rp = gen.substring(i,i+3);			
			s2.append(letras[Integer.parseInt(rp)]);
		}		
		String id = s2.toString();		
		id = id.substring(0,8) + "-" + id.substring(8,16) + "-" + id.substring(16,24); 
		return id;						
	}
		
}
