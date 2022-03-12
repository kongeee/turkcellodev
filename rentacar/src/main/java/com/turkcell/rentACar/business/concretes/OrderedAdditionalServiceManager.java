package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.dtos.OrderedAdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService 
{

	private OrderedAdditionalServiceDao orderedOrderedAdditionalServiceDao;
	private ModelMapperService modelMapperService;
	private CarRentalService carRentalService;
	private AdditionalServiceService additionalServiceService;

	@Autowired
	public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedOrderedAdditionalServiceDao, ModelMapperService modelMapperService,
			@Lazy CarRentalService carRentalService, AdditionalServiceService additionalServiceService) 
	{
		this.orderedOrderedAdditionalServiceDao = orderedOrderedAdditionalServiceDao;
		this.modelMapperService = modelMapperService;
		this.carRentalService = carRentalService;
		this.additionalServiceService = additionalServiceService;
	}

	@Override
	public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException 
	{
		checkIfCarAdditionalServiceExistsById(createOrderedAdditionalServiceRequest.getAdditionalServiceId());
		checkIfCarRentalExistsById(createOrderedAdditionalServiceRequest.getCarRentalId());
		
		OrderedAdditionalService orderedOrderedAdditionalService = this.modelMapperService.forRequest().map(createOrderedAdditionalServiceRequest,OrderedAdditionalService.class);

		orderedOrderedAdditionalService.setOrderedAdditionalServiceId(0);
		this.orderedOrderedAdditionalServiceDao.save(orderedOrderedAdditionalService);

		return new SuccessResult("Ordered Additional Service Added Successfully");
	}

	@Override
	public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException 
	{
		
		checkIfCarAdditionalServiceExistsById(updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		checkIfCarRentalExistsById(updateOrderedAdditionalServiceRequest.getCarRentalId());
		checkIfExistByOrderedAdditionalServiceId(updateOrderedAdditionalServiceRequest.getAdditionalServiceId());

		OrderedAdditionalService orderedOrderedAdditionalService = this.orderedOrderedAdditionalServiceDao.getById(updateOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());
		orderedOrderedAdditionalService = this.modelMapperService.forRequest().map(updateOrderedAdditionalServiceRequest,OrderedAdditionalService.class);
		this.orderedOrderedAdditionalServiceDao.save(orderedOrderedAdditionalService);

		return new SuccessResult("Ordered Additional Service Updated Successfully");
	}

	@Override
	public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException 
	{
		checkIfExistByOrderedAdditionalServiceId(deleteOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());

		OrderedAdditionalService orderedOrderedAdditionalService = this.modelMapperService.forRequest().map(deleteOrderedAdditionalServiceRequest,OrderedAdditionalService.class);
		this.orderedOrderedAdditionalServiceDao.delete(orderedOrderedAdditionalService);
		return new SuccessResult("Ordered Additional Service Deleted Successfully");
	}

	@Override
	public DataResult<List<OrderedAdditionalServiceListDto>> getAll() 
	{
		List<OrderedAdditionalService> result=this.orderedOrderedAdditionalServiceDao.findAll();

		List<OrderedAdditionalServiceListDto> response = result.stream().map(orderedOrderedAdditionalService->this.modelMapperService.forDto().map(orderedOrderedAdditionalService,OrderedAdditionalServiceListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<OrderedAdditionalServiceListDto>>(response,"Ordered Additional Services Listed Successfully");
	}

	@Override
	public DataResult<OrderedAdditionalServiceDto> getById(int orderedOrderedAdditionalServiceId) throws BusinessException 
	{
		checkIfExistByOrderedAdditionalServiceId(orderedOrderedAdditionalServiceId);

		OrderedAdditionalService orderedOrderedAdditionalService = orderedOrderedAdditionalServiceDao.getById(orderedOrderedAdditionalServiceId);
		OrderedAdditionalServiceDto orderedOrderedAdditionalServiceDto = this.modelMapperService.forDto().map(orderedOrderedAdditionalService,OrderedAdditionalServiceDto.class);

		return new SuccessDataResult<OrderedAdditionalServiceDto>(orderedOrderedAdditionalServiceDto,"Ordered Additional Service Listed Successfully");
	}

	private void checkIfExistByOrderedAdditionalServiceId(int orderedAdditionalServiceId) throws BusinessException 
	{
		if(!this.orderedOrderedAdditionalServiceDao.existsById(orderedAdditionalServiceId)) 
		{
			throw new BusinessException("Ordered Additional Service not found");
		}
	}
	
	private void checkIfCarRentalExistsById(int carRentalId) throws BusinessException 
	{
		this.carRentalService.getById(carRentalId);
	}
	
	private void checkIfCarAdditionalServiceExistsById(int additionalServiceId) throws BusinessException 
	{
		this.additionalServiceService.getById(additionalServiceId);
	}
}
