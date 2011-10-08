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
	
	<c:forEach var="week_day_random" items="${week_day_random_entries}">
		<weekly_topic>
		    <id>${week_day_random.topic.id}</id>
			<year>${week_day_random.topic.topic_year}</year>
			<week>${week_day_random.topic.topic_week}</week>
			<topic><![CDATA[${week_day_random.topic.topic_name}]]></topic>
			<description><![CDATA[${week_day_random.topic.topic_description}]]></description>
			
		<weekday_topics>
			<c:forEach var="topic" items="${week_day_random.week_day_topics}">
				<entry>
					<id>${topic.id}</id>
					<date>${topic.topic_date}</date>
					<word><![CDATA[${topic.topic_word}]]></word>
					<definition><![CDATA[${topic.topic_definition}]]></definition>
					<imageurl><![CDATA[${topic.topic_image_url}]]></imageurl>
					<quote_en>
					<![CDATA[${topic.quote_en}]]>
					</quote_en>
					<quote_cn>
					<![CDATA[${topic.quote_cn}]]>
					</quote_cn>
				</entry>
			</c:forEach>
		</weekday_topics>
		</weekly_topic>
	</c:forEach>
	
</result>