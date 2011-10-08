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
	<weekly_topics>
		<c:forEach var="topic" items="${topics}">
		<entry>
			<id>${topic.id}</id>
			<year>${topic.topic_year}</year>
			<week>${topic.topic_week}</week>
			<topic><![CDATA[${topic.topic_name}]]></topic>
			<description><![CDATA[${topic.topic_description}]]></description>
		</entry>
	</c:forEach>
	</weekly_topics>
</result>