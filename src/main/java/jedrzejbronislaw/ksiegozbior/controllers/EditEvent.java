package jedrzejbronislaw.ksiegozbior.controllers;

import org.springframework.context.ApplicationEvent;

import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import lombok.Getter;

public class EditEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1011027258866928623L;

	@Getter private Ent entity;
	
	
	public EditEvent(Object source, Ent entity) {
		super(source);
		this.entity = entity;
	}
}
