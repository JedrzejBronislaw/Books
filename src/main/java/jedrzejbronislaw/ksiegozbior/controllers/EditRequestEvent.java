package jedrzejbronislaw.ksiegozbior.controllers;

import org.springframework.context.ApplicationEvent;

import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import lombok.Getter;

public class EditRequestEvent extends ApplicationEvent {

	private static final long serialVersionUID = 217317930380616134L;
	
	@Getter private Ent entity;
	
	
	public EditRequestEvent(Object source, Ent entity) {
		super(source);
		this.entity = entity;
	}
}
