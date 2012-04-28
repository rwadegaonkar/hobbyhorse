<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function createUserObject($request) {
    if (isset($_SESSION['loginType']) && $_SESSION['loginType'] == 2) {
        $data['name'] = $request->name;
        $data['username'] = $request->username;
        $data['hobbies'] = $_SESSION['likes']->data[0]->name; 
        $data['loginTypeId'] = 2;
        $data['createdBy'] = $request->username;
        $data['lastUpdatedBy'] = $request->username;
        $data['email'] = "";
        $data['skills'] = "";
        $data['location'] = "";
        $data['password'] = $request->id;
    } else {
        $data['name'] = $request['firstName'];
        $data['username'] = $request['username'];
        $data['password'] = $request['password'];
        $data['email'] = $request['email'];
        $data['skills'] = $request['skills'];
        $data['hobbies'] = $request['hobbies'];
        $data['location'] = $request['city'];
        $data['loginTypeId'] = 1;
        $data['createdBy'] = $request['username'];
        $data['lastUpdatedBy'] = $request['username'];
    }
    return $data;
}

function createLessonObject($request) {
    $data['name'] = $request['name'];
    $data['description'] = $request['description'];
    $data['eventDate'] = $request['eventDate'];
    $data['eventTime'] = $request['eventTime'];
    $data['createdBy'] = $_SESSION['user']->username;
    $data['lastUpdatedBy'] = $_SESSION['user']->username;
    $data['lessonTypeId'] = $request['lessonTypeId'];
    $data['sessionId'] = $request['sessionId'];
    return $data;
}

function createParticipantObject($request) {
    $data['name'] = $request['lessonName'];
    $data['createdBy'] = $_SESSION['user']->username;
    $data['lastUpdatedBy'] = $_SESSION['user']->username;
    $data['lessonId'] = $request['lessonId'];
    $data['userId'] = $request['userId'];
    return $data;
}


function createCommentObject($request) {
    $data['name'] = $request['lessonName'];
    $data['description'] = $request['description'];
    $data['lessonId'] = $request['lessonId'];
    $data['userId'] = $request['userId'];
    $data['createdBy'] = $_SESSION['user']->username;
    $data['lastUpdatedBy'] = $_SESSION['user']->username;
    $data['rating'] = $request['rating'];
    return $data;
}

?>
