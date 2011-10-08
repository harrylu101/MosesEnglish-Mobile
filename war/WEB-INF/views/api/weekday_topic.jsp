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
	
	<weekly_topic>
	 <id>${topic.id}</id>
		<year>${topic.topic_year}</year>
		<week>${topic.topic_week}</week>
		<topic><![CDATA[${topic.topic_name}]]></topic>
		<description><![CDATA[${topic.topic_description}]]></description>
		
	<weekday_topics>
		<c:forEach var="topic" items="${week_day_topics}">
			<entry>
			<id>${topic.id}</id>
			<date>${topic.topic_date}</date>
			<word><![CDATA[${topic.topic_word}]]></word>
			<definition><![CDATA[${topic.topic_definition}]]></definition>
			<imageurl><![CDATA[${topic.topic_image_url}]]></imageurl>
			<quote_en><![CDATA[${topic.quote_en}]]></quote_en>
			<quote_cn><![CDATA[${topic.quote_cn}]]></quote_cn>
			</entry>
		</c:forEach>
	</weekday_topics>
		
	</weekly_topic>
	
	
</result>