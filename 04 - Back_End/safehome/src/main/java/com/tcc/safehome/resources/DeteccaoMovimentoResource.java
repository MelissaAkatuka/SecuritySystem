package com.tcc.safehome.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tcc.safehome.domain.DeteccaoMovimento;
import com.tcc.safehome.dto.DeteccaoMovimentoDTO;
import com.tcc.safehome.services.DeteccaoMovimentoService;

@RestController
@RequestMapping(value="/deteccao-movimento")
public class DeteccaoMovimentoResource {
	
	@Autowired
	private DeteccaoMovimentoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<DeteccaoMovimento> find(@PathVariable Integer id){
		DeteccaoMovimento obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody DeteccaoMovimento obj){
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody DeteccaoMovimento obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<DeteccaoMovimentoDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer  page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction){
		Page<DeteccaoMovimento> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<DeteccaoMovimentoDTO> listDto = list.map(obj -> new DeteccaoMovimentoDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}

}
