package com.gaurav.UrlShortnerReactiveMongo.utility;

import org.springframework.beans.BeanUtils;

import com.gaurav.UrlShortnerReactiveMongo.dto.UrlDto;
import com.gaurav.UrlShortnerReactiveMongo.dto.UrlReportDto;
import com.gaurav.UrlShortnerReactiveMongo.entity.Url;
import com.gaurav.UrlShortnerReactiveMongo.entity.UrlReport;

public class Utils {

	public static Url dtoToEntity(UrlDto urlDto) {
		Url url = new Url();
		BeanUtils.copyProperties(urlDto, url);
		return url; 
	}
	public static UrlDto entityToDto(Url url) {
		UrlDto urlDto = new UrlDto();
		BeanUtils.copyProperties(url, urlDto);
		return urlDto; 
	}
	public static UrlReport reportDtoToreportEntity(UrlReportDto urlreportDto) {
		UrlReport urlreport = new UrlReport();
		BeanUtils.copyProperties(urlreportDto, urlreport);
		return urlreport;
	}
	public static UrlReportDto reportEntityToreportDto(UrlReport urlreport) {
		UrlReportDto urlreportDto = new UrlReportDto();
		BeanUtils.copyProperties(urlreport, urlreportDto);
		return urlreportDto;
	} 
}