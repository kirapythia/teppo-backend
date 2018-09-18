package fi.espoo.pythia.backend.repos.entities;

import java.io.Serializable;

public enum Status implements Serializable{

	WAITING_FOR_APPROVAL,
	APPROVED,
	REVERTED
}
