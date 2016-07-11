package br.ufpi.ardigital.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ArDigitalLoginTest.class,
	ArDigitalSendDocumentTest.class,
	ArDigitalMonitorSendingTest.class,
	ArDigitalApproveDocumentTest.class,
	ArDigitalDownloadDocument.class,
	ArDigitalManagePermissions.class
})
public class ArDigitalTestSuit {

}
