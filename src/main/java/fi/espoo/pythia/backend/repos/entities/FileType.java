package fi.espoo.pythia.backend.repos.entities;

import java.io.Serializable;

public enum FileType implements Serializable {
	REFERENCE,
	TEXT_TABLE_PRESENTATION,
	IMAGE,
	EDGE_MEASUREMENT_LINE,
	FIELD_DATA,
	DRILLING_BORING,
	HANSU
}
