package com.turkcell.northwind.core.utilities.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
public class ModelMapperManager implements ModelMapperService {
	
	private ModelMapper modelMapper;
	
	public ModelMapperManager(ModelMapper modelMapper) {
		
		this.modelMapper = modelMapper;
	}
	
	
	
	
	@Override
	public ModelMapper forDto() {
		//elemanları isimlerine göre eşleştirir (sadece eşleştirebildikleri)
		this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper;
	}
	
	
	
	@Override
	public ModelMapper forRequest() {
		//elemanları isimlerine göre eşleştirir (eşleştirilmek istenen elemenaların tam oalrak eşlenmesi gerekiyor)
		this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper;
	}

}
