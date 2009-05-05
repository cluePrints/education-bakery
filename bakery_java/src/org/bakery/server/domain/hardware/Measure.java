package org.bakery.server.domain.hardware;

import java.util.Date;

import org.bakery.server.domain.BusinessEntity;
import org.bakery.server.validation.CouldNotBeEmpty;
/**
 * Represent value of specific parameter at specific time.
 * 
 * For example, temperature at a fridge1_sectionB at 23:05
 * @author Ivan_Sobolev1
 *
 */
public class Measure extends BusinessEntity {
	private static final long serialVersionUID=1L;
	/**
	 * Value
	 */
	private Double value;
	
	/**
	 * Measured parameter
	 */
	private DeviceParameter parameter;
	
	/**
	 * Date & Time of a measure
	 */
	private Date time=(Date) NULL_DATE.clone();

	@CouldNotBeEmpty(message="���������� �������� ��������� ������ ���� �������.")
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	@CouldNotBeEmpty(message="���������� ���������������� �������� ������ ���� ������.")
	public DeviceParameter getParameter() {
		return parameter;
	}
	public void setParameter(DeviceParameter parameter) {
		this.parameter = parameter;
	}
	
	@CouldNotBeEmpty(message="����� ������������ ��������� �������� ����������������� ��������� ������ ���� �������.")
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}		
}
