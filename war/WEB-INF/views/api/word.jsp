<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<result>
	<response> 
		<status>${repsonse.status}</status>
		<message>${repsonse.message}</message>
		<datasize>${repsonse.size}</datasize>
	</response>
	
	<weekday_topic>
			<id>${week_day_topic.id}</id>
			<date>${week_day_topic.topic_date}</date>
			<topic><![CDATA[${week_day_topic.topic_word}]]></topic>
			<definition><![CDATA[${week_day_topic.topic_definition}]]></definition>
			<imageurl><![CDATA[${week_day_topic.topic_image_url}]]></imageurl>
			<quote_en><![CDATA[${week_day_topic.quote_en}]]></quote_en>
			<quote_cn><![CDATA[${week_day_topic.quote_cn}]]></quote_cn>
			
			<words>
					<c:forEach var="word" items="${words}">
			    <entry>
					<id>${word.id}</id>
					<word><![CDATA[${word.word}]]></word>
					<definition><![CDATA[${word.defintion}]]></definition>
				</entry>
					</c:forEach>
			</words>
	</weekday_topic>
	
</result>