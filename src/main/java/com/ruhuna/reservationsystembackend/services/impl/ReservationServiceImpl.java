package com.ruhuna.reservationsystembackend.services.impl;

import com.ruhuna.reservationsystembackend.dto.ReservationDto;
import com.ruhuna.reservationsystembackend.dto.UnavailableDatesDto;
import com.ruhuna.reservationsystembackend.entity.Reservation;
import com.ruhuna.reservationsystembackend.enums.ApprovalStatus;
import com.ruhuna.reservationsystembackend.repository.ReservationRepository;
import com.ruhuna.reservationsystembackend.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;

    //get all reservations
    @Override
    public List<UnavailableDatesDto> getUnavailableDates() {
        try {
            List<Reservation> reservations = reservationRepository.findAll();
            return reservations.stream()
                    .map(reservation -> new UnavailableDatesDto(
                            reservation.getReservedDate(),
                            reservation.getOrganizationName(),
                            reservation.getEventType()))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw e;
        }
    }

    //submit application form
    @Override
    public void submitForm(ReservationDto reservationDto) {
        try {
            Reservation reservation = modelMapper.map(reservationDto, Reservation.class);
            if (reservationDto.getOrganizationName() != null && !reservationDto.getOrganizationName().isEmpty())
                reservation.setOrganizationName(reservationDto.getOrganizationName());
            if (reservationDto.getApplicantName() != null && !reservationDto.getApplicantName().isEmpty())
                reservation.setApplicantName(reservationDto.getApplicantName());
            if (reservationDto.getNic() != null && !reservationDto.getNic().isEmpty())
                reservation.setNic(reservationDto.getNic());
            if (reservationDto.getEmail() != null && !reservationDto.getEmail().isEmpty())
                reservation.setEmail(reservationDto.getEmail());
            if (reservationDto.getMobile() != null && !reservationDto.getMobile().isEmpty())
                reservation.setMobile(reservationDto.getMobile());
            if (reservationDto.getLandLine() != null && !reservationDto.getLandLine().isEmpty())
                reservation.setLandLine(reservationDto.getLandLine());
            if (reservationDto.getAddress() != null && !reservationDto.getAddress().isEmpty())
                reservation.setAddress(reservationDto.getAddress());
            if(reservationDto.getEventType() != null && !reservationDto.getEventType().isEmpty())
                reservation.setEventType(reservationDto.getEventType());
            if(reservationDto.getViewers() != null && !reservationDto.getViewers().isEmpty())
                reservation.setViewers(reservationDto.getViewers());
            if(reservationDto.getReservedDate() != null)
                reservation.setReservedDate(reservationDto.getReservedDate());
            if(reservationDto.getEventStartTime() != null)
                reservation.setEventStartTime(reservationDto.getEventStartTime());
            if(reservationDto.getEventEndTime() != null)
                reservation.setEventEndTime(reservationDto.getEventEndTime());
            if(reservationDto.getEventNoOfHours() != null)
                reservation.setEventNoOfHours(reservationDto.getEventNoOfHours());
            if(reservationDto.getSecurity() != null && !reservationDto.getSecurity().isEmpty())
                reservation.setSecurity(reservationDto.getSecurity());
            reservation.setOtherEventType(reservationDto.getOtherEventType());
            reservation.setConcertType(reservationDto.getConcertType());
            reservation.setMusicBand(reservationDto.getMusicBand());
            reservation.setSingers(reservationDto.getSingers());
            reservation.setSpecialInvitees(reservationDto.getSpecialInvitees());
            reservation.setEventAdditionalHours(reservationDto.getEventAdditionalHours());
            reservation.setDecorationStartTime(reservationDto.getDecorationStartTime());
            reservation.setDecorationEndTime(reservationDto.getDecorationEndTime());
            reservation.setDecorationNoOfHours(reservationDto.getDecorationNoOfHours());
            reservation.setRehearsalStartTime(reservationDto.getRehearsalStartTime());
            reservation.setRehearsalEndTime(reservationDto.getRehearsalEndTime());
            reservation.setRehearsalNoOfHours(reservationDto.getRehearsalNoOfHours());
            reservation.setStageLighting(reservationDto.getStageLighting());
            reservation.setStageSoundAdministration(reservationDto.getStageSoundAdministration());
            reservation.setStageDecoration(reservationDto.getStageDecoration());
            reservation.setElectricGenerator(reservationDto.getElectricGenerator());
            reservation.setTicketSalesAtPremises(reservationDto.getTicketSalesAtPremises());
            reservation.setApprovalStatus(ApprovalStatus.PENDING);

            //set advance fee
            reservation.setAdvanceFee(BigDecimal.valueOf(100000));

            //set refundable fee and hall reservation fee
            switch (reservationDto.getEventType()) {
                case "Stage Drama", "Conferences/Lectures", "Awards/Tributes/Ceremonies", "Other" -> {
                    reservation.setRefundableFee(BigDecimal.valueOf(250000));
                    reservation.setHallReservationFee(reservationDto.getEventNoOfHours() == 4 ? BigDecimal.valueOf(350000) : BigDecimal.valueOf(750000));
                }
                case "Musical concerts" -> {
                    reservation.setRefundableFee(BigDecimal.valueOf(1000000));
                    reservation.setHallReservationFee(reservationDto.getEventNoOfHours() == 4 ? BigDecimal.valueOf(500000) : BigDecimal.valueOf(750000));
                }
                default -> throw new IllegalArgumentException("Invalid event type");
            }

            //calculate additional hour fee
            reservation.setAdditionalHourFee(reservationDto.getEventAdditionalHours() != null && reservationDto.getEventAdditionalHours() > 0
                    ? BigDecimal.valueOf(reservationDto.getEventAdditionalHours() * 100000)
                    : BigDecimal.ZERO);

            //calculate decoration fee
            reservation.setDecorationFee(reservationDto.getDecorationNoOfHours() != null && reservationDto.getDecorationNoOfHours() > 0
                    ? BigDecimal.valueOf(reservationDto.getDecorationNoOfHours() * 7500)
                    : BigDecimal.ZERO);

            //calculate rehearsal fee
            reservation.setRehearsalFee(reservationDto.getRehearsalNoOfHours() != null && reservationDto.getRehearsalNoOfHours() > 0
                    ? BigDecimal.valueOf(reservationDto.getRehearsalNoOfHours() * 20000)
                    : BigDecimal.ZERO);

            //set total fee
            reservation.setTotalFee(reservation.getHallReservationFee()
                    .add(reservation.getRefundableFee())
                    .add(reservation.getDecorationFee())
                    .add(reservation.getRehearsalFee())
                    .add(reservation.getAdditionalHourFee()));

            reservationRepository.save(reservation);

        } catch (Exception e) {
            throw e;
        }
    }
}
