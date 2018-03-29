package openadmin.model;

import java.time.LocalDate;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class Audit {

	@Getter @Setter
	private String lastUser;
	@Getter @Setter
	private LocalDate data;
}
