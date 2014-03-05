package it.unibo.games.rpsls.tests.sib;

import sofia_kp.KPICore;

public class PlayerTest {
	public static void main(String[] args) {
		KPICore kp = new KPICore("192.168.0.3", 10010, "X");
		
		kp.insert("Player_123", "rdf:type", "Player", "uri", "uri");
		kp.insert("Ppa_124", "rdf:type", "PADADA", "uri", "uri");
		
	}
}
