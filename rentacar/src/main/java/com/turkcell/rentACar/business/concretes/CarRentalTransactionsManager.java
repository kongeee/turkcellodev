package com.turkcell.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACar.api.models.CarRentalReturnProcessInformationForCorporateModel;
import com.turkcell.rentACar.api.models.CarRentalReturnProcessInformationForIndividualModel;
import com.turkcell.rentACar.api.models.CarRentalTransactionInformationForCorporateCustomerModel;
import com.turkcell.rentACar.api.models.CarRentalTransactionInformationForIndividualCustomerModel;
import com.turkcell.rentACar.api.models.ReturnTheBeRentalCarForCorporateCustomerModel;
import com.turkcell.rentACar.api.models.ReturnTheBeRentalCarForIndividualCustomerModel;
import com.turkcell.rentACar.api.models.Calculates.AdditionalService.CalculateAdditionalServiceForCorporateCustomerModel;
import com.turkcell.rentACar.api.models.Calculates.AdditionalService.CalculateAdditionalServiceForIndividualCustomerModel;
import com.turkcell.rentACar.api.models.Calculates.CarRental.CalculateCarRentalForCorporateCustomerModel;
import com.turkcell.rentACar.api.models.Calculates.CarRental.CalculateCarRentalForIndividualCustomerModel;
import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.CarRentalTransactionsService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.abstracts.PosService;
import com.turkcell.rentACar.business.dtos.CarRentalDto;
import com.turkcell.rentACar.business.dtos.InvoiceDto;
import com.turkcell.rentACar.business.dtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalForCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalForIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.creates.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.creates.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalForCorporateCustomerRequest;
import com.turkcell.rentACar.business.utilities.dateOperations;
import com.turkcell.rentACar.core.utilities.bankServices.BankInformation;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarRentalTransactionsManager implements CarRentalTransactionsService
{
    private ModelMapperService modelMapperService;

    private PosService posService;

    private CarService carService;
    private CarRentalService carRentalService;
    private OrderedAdditionalServiceService orderedAdditionalServiceService;
    private AdditionalServiceService additionalServiceService;
    private InvoiceService invoiceService;
    private PaymentService paymentService;

    public CarRentalTransactionsManager(ModelMapperService modelMapperService, CarService carService, CarRentalService carRentalService, PosService posService,
    OrderedAdditionalServiceService orderedAdditionalServiceService,
    AdditionalServiceService additionalServiceService,InvoiceService invoiceService,PaymentService paymentService) 
    {
        this.modelMapperService = modelMapperService;

        this.posService = posService;

        this.carService = carService;
        this.carRentalService = carRentalService;
        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
        this.additionalServiceService = additionalServiceService;
        this.invoiceService = invoiceService;
        this.paymentService = paymentService;
    }

    @Override
    @Transactional
    public Result MakeACarRentalForCorporateCustomer(CarRentalTransactionInformationForCorporateCustomerModel carRentalTransactionInformationForCorporateCustomerModel) throws BusinessException 
    {
        CalculateCarRentalForCorporateCustomerModel calculateCarRentalForCorporateCustomerModel = this.modelMapperService.forRequest().map(carRentalTransactionInformationForCorporateCustomerModel, CalculateCarRentalForCorporateCustomerModel.class);
        CalculateAdditionalServiceForCorporateCustomerModel calculateAdditionalServiceForCorporateCustomerModel = this.modelMapperService.forRequest().map(carRentalTransactionInformationForCorporateCustomerModel, CalculateAdditionalServiceForCorporateCustomerModel.class);

        double price = calculateCarRentalTotalPriceForCorporateCustomer(calculateCarRentalForCorporateCustomerModel, calculateAdditionalServiceForCorporateCustomerModel);

        makeAMoneyPayment(carRentalTransactionInformationForCorporateCustomerModel.getBankInformation(),price);

        CarRentalDto carRentalDto = insertCarRental(carRentalTransactionInformationForCorporateCustomerModel);

        insertOrderedAdditionalService(carRentalTransactionInformationForCorporateCustomerModel.getOrderedAdditionalServiceIds(),carRentalDto.getCar_CarId());

        InvoiceDto invoiceDto = insertInvoice(carRentalDto.getCarRentalId(), carRentalTransactionInformationForCorporateCustomerModel.getCorporateCustomerId(), price);

        insertPayment(invoiceDto.getInvoiceId(),carRentalTransactionInformationForCorporateCustomerModel.getCorporateCustomerId(),price);

        return new SuccessResult();
    }

    @Override
    @Transactional
    public Result MakeACarRentalForIndivualCustomer(CarRentalTransactionInformationForIndividualCustomerModel carRentalTransactionInformationForIndiviualCustomerModel) throws BusinessException 
    {
        CalculateCarRentalForIndividualCustomerModel calculateCarRentalForCorporateCustomerModel = this.modelMapperService.forRequest().map(carRentalTransactionInformationForIndiviualCustomerModel, CalculateCarRentalForIndividualCustomerModel.class);
        CalculateAdditionalServiceForIndividualCustomerModel calculateAdditionalServiceForCorporateCustomerModel = this.modelMapperService.forRequest().map(carRentalTransactionInformationForIndiviualCustomerModel, CalculateAdditionalServiceForIndividualCustomerModel.class);

        double price = calculateCarRentalTotalPriceForIndividualCustomer(calculateCarRentalForCorporateCustomerModel, calculateAdditionalServiceForCorporateCustomerModel);

        makeAMoneyPayment(carRentalTransactionInformationForIndiviualCustomerModel.getBankInformation(),price);

        CarRentalDto carRentalDto = insertCarRental(carRentalTransactionInformationForIndiviualCustomerModel);

        insertOrderedAdditionalService(carRentalTransactionInformationForIndiviualCustomerModel.getOrderedAdditionalServiceIds(),carRentalDto.getCarRentalId());

        InvoiceDto invoiceDto = insertInvoice(carRentalDto.getCarRentalId(), carRentalTransactionInformationForIndiviualCustomerModel.getIndividualCustomerId(), price);

        insertPayment(invoiceDto.getInvoiceId(),carRentalTransactionInformationForIndiviualCustomerModel.getIndividualCustomerId(),price);

        return new SuccessResult();
    }

    @Override
    @Transactional
    public Result ReturnTheRentalCarForCorporateCustomer(CarRentalReturnProcessInformationForCorporateModel carRentalReturnProcessInformationForCorporateModel)throws BusinessException 
    {
        CarRentalDto carRentalDto = this.carRentalService.getById(carRentalReturnProcessInformationForCorporateModel.getCarRentalId()).getData();

        checkForLateReturn(carRentalDto, carRentalReturnProcessInformationForCorporateModel.getReturnDate());

        updateOfRentalCarReturnKilometerForCoporateCustomer(carRentalReturnProcessInformationForCorporateModel.getCarRentalId(), 
        carRentalReturnProcessInformationForCorporateModel.getCorporateCustomerId(), carRentalReturnProcessInformationForCorporateModel.getReturnKilometer());

        updateOfKilometresForCar(carRentalReturnProcessInformationForCorporateModel.getCarRentalId(),carRentalReturnProcessInformationForCorporateModel.getReturnKilometer());
    
        return new SuccessResult();
    }

    @Override
    @Transactional
    public Result ReturnTheRentalCarForIndivualCustomer(CarRentalReturnProcessInformationForIndividualModel carRentalReturnProcessInformationForIndividualModel) throws BusinessException 
    {
        CarRentalDto carRentalDto = this.carRentalService.getById(carRentalReturnProcessInformationForIndividualModel.getCarRentalId()).getData();

        checkForLateReturn(carRentalDto, carRentalReturnProcessInformationForIndividualModel.getReturnDate());

        updateOfRentalCarReturnKilometerForIndividualCustomer(carRentalReturnProcessInformationForIndividualModel.getCarRentalId(), 
        carRentalReturnProcessInformationForIndividualModel.getIndividualCustomerId(), carRentalReturnProcessInformationForIndividualModel.getReturnKilometer());

        updateOfKilometresForCar(carRentalDto.getCar_CarId(), carRentalReturnProcessInformationForIndividualModel.getReturnKilometer());
    
        return new SuccessResult();
    }

    @Override
    @Transactional
    public Result ReturnTheBeTardyRentalCarForCorporateCustomer(ReturnTheBeRentalCarForCorporateCustomerModel returnTheBeRentalCarForCorporateCustomerModel) throws BusinessException 
    {
        CarRentalDto carRentalDto = this.carRentalService.getById(returnTheBeRentalCarForCorporateCustomerModel.getCarRentalId()).getData();

        updateCarRental(returnTheBeRentalCarForCorporateCustomerModel);

        List<Integer> orderedAdditionalServiceIds = findOrderedAdditionalServiceIds(carRentalDto.getCarRentalId());

        CalculateCarRentalForCorporateCustomerModel calculateCarRentalForCorporateCustomerModel = new CalculateCarRentalForCorporateCustomerModel(carRentalDto.getCar_CarId(),
            carRentalDto.getReturnDate(), returnTheBeRentalCarForCorporateCustomerModel.getReturnDate(), carRentalDto.getStartCity_CityId(), carRentalDto.getEndCity_cityId());
        CalculateAdditionalServiceForCorporateCustomerModel calculateAdditionalServiceForCorporateCustomerModel = new CalculateAdditionalServiceForCorporateCustomerModel(
            orderedAdditionalServiceIds, carRentalDto.getReturnDate(), returnTheBeRentalCarForCorporateCustomerModel.getReturnDate());

        double price = calculateCarRentalTotalPriceForCorporateCustomer(calculateCarRentalForCorporateCustomerModel, calculateAdditionalServiceForCorporateCustomerModel);

        InvoiceDto invoiceDto = insertInvoice(returnTheBeRentalCarForCorporateCustomerModel.getCarRentalId(), returnTheBeRentalCarForCorporateCustomerModel.getCorporateCustomerId(), price);
        
        insertPayment(carRentalDto.getCar_CarId(), invoiceDto.getInvoiceId(), price);

        return new SuccessResult();
    }

    @Override
    @Transactional
    public Result ReturnTheBeTardyRentalCarForIndivualCustomer(ReturnTheBeRentalCarForIndividualCustomerModel returnTheBeRentalCarForIndividualCustomerModel) throws BusinessException 
    {
        CarRentalDto carRentalDto = this.carRentalService.getById(returnTheBeRentalCarForIndividualCustomerModel.getCarRentalId()).getData();

        updateCarRental(returnTheBeRentalCarForIndividualCustomerModel);

        List<Integer> orderedAdditionalServiceIds = findOrderedAdditionalServiceIds(carRentalDto.getCarRentalId());

        CalculateCarRentalForCorporateCustomerModel calculateCarRentalForCorporateCustomerModel = new CalculateCarRentalForCorporateCustomerModel(carRentalDto.getCar_CarId(),
            carRentalDto.getReturnDate(), returnTheBeRentalCarForIndividualCustomerModel.getReturnDate(), carRentalDto.getStartCity_CityId(), carRentalDto.getEndCity_cityId());
        CalculateAdditionalServiceForCorporateCustomerModel calculateAdditionalServiceForCorporateCustomerModel = new CalculateAdditionalServiceForCorporateCustomerModel(
            orderedAdditionalServiceIds, carRentalDto.getReturnDate(), returnTheBeRentalCarForIndividualCustomerModel.getReturnDate());

        double price = calculateCarRentalTotalPriceForCorporateCustomer(calculateCarRentalForCorporateCustomerModel, calculateAdditionalServiceForCorporateCustomerModel);

        InvoiceDto invoiceDto = insertInvoice(returnTheBeRentalCarForIndividualCustomerModel.getCarRentalId(), returnTheBeRentalCarForIndividualCustomerModel.getIndividualCustomerId(), price);
        
        insertPayment(returnTheBeRentalCarForIndividualCustomerModel.getIndividualCustomerId(), invoiceDto.getInvoiceId(), price);

        return new SuccessResult();
    }

    private void makeAMoneyPayment(BankInformation bankInformation, double price) throws BusinessException
    {
        this.posService.pay(bankInformation,price);
    }

    private CarRentalDto insertCarRental(CarRentalTransactionInformationForCorporateCustomerModel carRentalTransactionInformationForCorporateCustomerModel)throws BusinessException 
    {
        CreateCarRentalForCorporateCustomerRequest createCarRentalForCorporateCustomerRequest= this.modelMapperService.forRequest()
        .map(carRentalTransactionInformationForCorporateCustomerModel, CreateCarRentalForCorporateCustomerRequest.class);

        double kilometerInformation = this.carService.getById(carRentalTransactionInformationForCorporateCustomerModel.getCarId()).getData().getKilometerInformation();

        createCarRentalForCorporateCustomerRequest.setStartingKilometer(kilometerInformation);
        createCarRentalForCorporateCustomerRequest.setReturnKilometer(kilometerInformation);

        CarRentalDto carRentalDto = this.carRentalService.rentForCorporateCustomer(createCarRentalForCorporateCustomerRequest).getData();

        return carRentalDto;
    }
    
    private CarRentalDto insertCarRental(CarRentalTransactionInformationForIndividualCustomerModel carRentalTransactionInformationForIndiviualCustomerModel)throws BusinessException 
    {
        CreateCarRentalForIndividualCustomerRequest createCarRentalForIndividualCustomerRequest= this.modelMapperService.forRequest()
        .map(carRentalTransactionInformationForIndiviualCustomerModel, CreateCarRentalForIndividualCustomerRequest.class);

        double kilometerInformation = this.carService.getById(createCarRentalForIndividualCustomerRequest.getCarId()).getData().getKilometerInformation();

        createCarRentalForIndividualCustomerRequest.setStartingKilometer(kilometerInformation);
        createCarRentalForIndividualCustomerRequest.setReturnKilometer(kilometerInformation);

        CarRentalDto carRentalDto = this.carRentalService.rentForIndividualCustomer(createCarRentalForIndividualCustomerRequest).getData();

        return carRentalDto;
    }

    private void insertOrderedAdditionalService(List<Integer> orderedAdditionalServiceIds, int carRentalId) throws BusinessException
    {
        this.orderedAdditionalServiceService.addRange(orderedAdditionalServiceIds, carRentalId);
    }

    private InvoiceDto insertInvoice(int carRentalId, int customerId,double price) throws BusinessException
    {
        CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest(LocalDate.now(), price, carRentalId, customerId);

        InvoiceDto invoiceDto = this.invoiceService.add(createInvoiceRequest).getData();

        return invoiceDto;
    }

    private void insertPayment(int customerId, int invoiceId, double price) throws BusinessException
    {
        CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest(customerId, invoiceId, price);
        this.paymentService.add(createPaymentRequest);
    }

    private double calculateCarRentalTotalPriceForCorporateCustomer(CalculateCarRentalForCorporateCustomerModel calculateCarRentalForCorporateCustomerModel, 
        CalculateAdditionalServiceForCorporateCustomerModel calculateAdditionalServiceForCorporateCustomerModel) throws BusinessException
    {
        double price = 0.0;

        price = this.carRentalService.calculatePriceForCorporateCustomer(calculateCarRentalForCorporateCustomerModel).getData();

        price += this.additionalServiceService.calculateAdditionalServicePriceForCorporateCustomer(calculateAdditionalServiceForCorporateCustomerModel).getData();

        return price;
    }

    private double calculateCarRentalTotalPriceForIndividualCustomer(CalculateCarRentalForIndividualCustomerModel carRentalTransactionInformationForIndividaulCustomerModel, CalculateAdditionalServiceForIndividualCustomerModel calculateAdditionalServiceForIndividaulCustomerModel) throws BusinessException
    {
        double price = 0.0;

        price = this.carRentalService.calculatePriceForIndividualCustomer(carRentalTransactionInformationForIndividaulCustomerModel).getData();

        price += this.additionalServiceService.calculateAdditionalServicePriceForIndividualCustomer(calculateAdditionalServiceForIndividaulCustomerModel).getData();

        return price;
    }

    private void updateCarRental(ReturnTheBeRentalCarForCorporateCustomerModel returnTheBeRentalCarForCorporateCustomerModel)throws BusinessException 
    {
        this.carRentalService.updateReturnKilometerAndReturnDateForCorporateCustomer(returnTheBeRentalCarForCorporateCustomerModel.getCarRentalId(), returnTheBeRentalCarForCorporateCustomerModel.getCorporateCustomerId(), 
            returnTheBeRentalCarForCorporateCustomerModel.getReturnKilometer(), returnTheBeRentalCarForCorporateCustomerModel.getReturnDate());
    }
    
    private void updateCarRental(ReturnTheBeRentalCarForIndividualCustomerModel rentalCarForIndividualCustomerModel)throws BusinessException 
    {
        this.carRentalService.updateReturnKilometerAndReturnDateForIndividualCustomer(rentalCarForIndividualCustomerModel.getCarRentalId(), rentalCarForIndividualCustomerModel.getIndividualCustomerId(), 
        rentalCarForIndividualCustomerModel.getReturnKilometer(), rentalCarForIndividualCustomerModel.getReturnDate());
    }

    private List<Integer> findOrderedAdditionalServiceIds(int carRentalId) throws BusinessException
    {
        List<OrderedAdditionalServiceListDto> orderedAdditionalServiceListDtos = this.orderedAdditionalServiceService.getAllByOrderedAdditionalService_CarRentalId(carRentalId).getData();
        
        List<Integer> orderedAdditionalServiceIds = orderedAdditionalServiceListDtos.stream().map((orderedAdditionalServiceDto) -> orderedAdditionalServiceDto.getAdditionalServiceId()).toList();

        return orderedAdditionalServiceIds;
    }

    private void updateOfRentalCarReturnKilometerForCoporateCustomer(int carRentalId, int corporateCustomerId, double newKilometers) throws BusinessException
    {
        this.carRentalService.updateReturnKilometerForCorporateCustomer(carRentalId,corporateCustomerId,newKilometers);
    }

    private void updateOfRentalCarReturnKilometerForIndividualCustomer(int carRentalId, int individualCustomerId, double newKilometers) throws BusinessException
    {
        this.carRentalService.updateReturnKilometerForIndividualCustomer(carRentalId,individualCustomerId,newKilometers);
    }

    private void updateOfKilometresForCar(int carRentalId, double returnKilometer) throws BusinessException 
    {
        this.carService.updateKilometerInformation(carRentalId, returnKilometer);
    }

    private void checkForLateReturn(CarRentalDto carRentalDto, LocalDate returnDate) throws BusinessException
    {
       if(carRentalDto.getReturnDate().isBefore(returnDate))
       {
           throw new BusinessException("");
       }
    }

}