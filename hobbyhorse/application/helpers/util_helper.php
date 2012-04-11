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
    } else {
        $data['name'] = $request['firstName'];
        $data['username'] = $request['username'];
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

?>
