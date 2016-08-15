package br.ufpi.ardigital.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ArDigitalLoginTest.class, //ok
	ArDigitalLogoutTest.class, //ok
	ArDigitalSendDocumentTest.class, //ok
	ArDigitalApproveDocumentTest.class, //ok
	ArDigitalManageDocumentsTest.class, //ok
	ArDigitalManagePermissionsTest.class, //ok	
})
public class ArDigitalTestSuit {

}
