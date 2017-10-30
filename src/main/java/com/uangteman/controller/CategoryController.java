package com.uangteman.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uangteman.dto.Result;
import com.uangteman.dto.SearchForm;
import com.uangteman.entity.Category;
import com.uangteman.repo.CategoryRepo;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryRepo repo;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> save(@Valid @RequestBody Category category, Errors errors) {
		Result result = new Result();
		
		if(errors.hasErrors()) {
			for(ObjectError err:errors.getAllErrors()) {
				result.getMessages().add(err.getDefaultMessage());
			}
			
			return ResponseEntity.badRequest().body(result);
		}
		
		Category output = repo.save(category);
		result.setPayload(output);
		return  ResponseEntity.ok(result);
		
	}

	@RequestMapping(method = RequestMethod.POST, value = "/search")
	public List<Category> findByName(@RequestBody SearchForm form) {
		return repo.findByNameIgnoringCase(form.getName());
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Category> findAll(){
		return repo.findAll();

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/like")
	public List<Category> findByNameLike(@RequestBody SearchForm form) {
		return repo.findByName("%"+form.getName()+"%");
	}
}
