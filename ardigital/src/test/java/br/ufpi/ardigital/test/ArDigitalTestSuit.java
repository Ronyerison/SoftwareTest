package br.ufpi.ardigital.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ArDigitalLoginTest.class,
	ArDigitalSendDocumentTest.class,
	ArDigitalMonitorSendingTest.class,
	ArDigitalApproveDocumentTest.class
})
public class ArDigitalTestSuit {

}
