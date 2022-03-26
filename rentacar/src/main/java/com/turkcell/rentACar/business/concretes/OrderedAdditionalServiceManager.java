package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
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

		return new SuccessResult(BusinessMessages.ORDERED_ADDITIONAL_ADDED);
	}

	@Override
	public Result addRange(List<Integer> orderedAdditionalServiceIds, int carRentalId) throws BusinessException 
	{
		for(var item : orderedAdditionalServiceIds)
		{
			CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest= new CreateOrderedAdditionalServiceRequest(carRentalId, item);

			this.add(createOrderedAdditionalServiceRequest);
		}

		return new SuccessResult(BusinessMessages.ORDERED_ADDITIONAL_LIST_ADDED);
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

		return new SuccessResult(BusinessMessages.ORDERED_ADDITIONAL_UPDATED);
	}

	@Override
	public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException 
	{
		checkIfExistByOrderedAdditionalServiceId(deleteOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());

		OrderedAdditionalService orderedOrderedAdditionalService = this.modelMapperService.forRequest().map(deleteOrderedAdditionalServiceRequest,OrderedAdditionalService.class);
		this.orderedOrderedAdditionalServiceDao.delete(orderedOrderedAdditionalService);
		return new SuccessResult(BusinessMessages.ORDERED_ADDITIONAL_DELETED);
	}

	@Override
	public DataResult<List<OrderedAdditionalServiceListDto>> getAll() 
	{
		List<OrderedAdditionalService> result=this.orderedOrderedAdditionalServiceDao.findAll();

		List<OrderedAdditionalServiceListDto> response = result.stream().map(orderedOrderedAdditionalService->this.modelMapperService.forDto().map(orderedOrderedAdditionalService,OrderedAdditionalServiceListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<OrderedAdditionalServiceListDto>>(response,BusinessMessages.ORDERED_ADDITIONAL_LISTED);
	}

	@Override
	public DataResult<OrderedAdditionalServiceDto> getById(int orderedOrderedAdditionalServiceId) throws BusinessException 
	{
		checkIfExistByOrderedAdditionalServiceId(orderedOrderedAdditionalServiceId);

		OrderedAdditionalService orderedOrderedAdditionalService = orderedOrderedAdditionalServiceDao.getById(orderedOrderedAdditionalServiceId);
		OrderedAdditionalServiceDto orderedOrderedAdditionalServiceDto = this.modelMapperService.forDto().map(orderedOrderedAdditionalService,OrderedAdditionalServiceDto.class);

		return new SuccessDataResult<OrderedAdditionalServiceDto>(orderedOrderedAdditionalServiceDto,BusinessMessages.ORDERED_ADDITIONAL_GETTED);
	}

	@Override
	public Result deleteAllByCarRentelId(int carRentalId) throws BusinessException 
	{
		this.orderedOrderedAdditionalServiceDao.deleteAllByCarRental_CarRentalId(carRentalId);

		return new SuccessResult();
	}

	@Override
	public DataResult<List<OrderedAdditionalServiceListDto>> getAllByOrderedAdditionalService_CarRentalId(int carRentalId) throws BusinessException 
	{
		checkIfCarRentalExistsById(carRentalId);

		List<OrderedAdditionalService> orderedAdditionalServices= this.orderedOrderedAdditionalServiceDao.getAllByOrderedAdditionalService_CarRentalId(carRentalId);

		List<OrderedAdditionalServiceListDto> orderedAdditionalServiceListDtos =orderedAdditionalServices.stream().map(orderedAdditionalService-> this.modelMapperService.forDto().map(orderedAdditionalService, OrderedAdditionalServiceListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<OrderedAdditionalServiceListDto>>(orderedAdditionalServiceListDtos);
	}

	private void checkIfExistByOrderedAdditionalServiceId(int orderedAdditionalServiceId) throws BusinessException 
	{
		if(!this.orderedOrderedAdditionalServiceDao.existsById(orderedAdditionalServiceId)) 
		{
			throw new BusinessException(BusinessMessages.ORDERED_ADDITIONAL_NOT_FOUND);
		}
	}
	
	private void checkIfCarRentalExistsById(int carRentalId) throws BusinessException 
	{
		this.carRentalService.checkIfCarRentalExistsById(carRentalId);
	}
	
	private void checkIfCarAdditionalServiceExistsById(int additionalServiceId) throws BusinessException 
	{
		this.additionalServiceService.checkIfExistByAdditionalServiceById(additionalServiceId);
	}
}
