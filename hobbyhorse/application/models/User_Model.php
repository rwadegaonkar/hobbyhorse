<?php

/**
 * Created by JetBrains PhpStorm.
 * User: radhika
 * Date: 3/18/12
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */
require_once('Platform/Webservices/Wrapper.php');
require_once('Platform/Data.php');

class User_Model extends CI_Model {

    private $myWrapper;

    public function __construct() {
        parent::__construct();
        $this->myWrapper = new Platform_Webservices_Wrapper();
    }

    public function getUsers() {
        $jsonObj = $this->myWrapper->request('users/');
        return Platform_Data::getDataObject($jsonObj);
    }

    public function getUserByUserId($userId, $method=null) {
        $jsonObj = $this->myWrapper->request('users/id/' . $userId);
        if (!$method) {
            return Platform_Data::getDataObject($jsonObj);
        } else {
            return $jsonObj;
        }
    }

    public function getUserByUsername($username, $method=null) {
        $jsonObj = $this->myWrapper->request('users/' . $username);
        if (!$method) {
            return Platform_Data::getDataObject($jsonObj);
        } else {
            return $jsonObj;
        }
    }

    public function checkLogin($data) {
        $jsonObj = $this->myWrapper->request('users/login', 'POST', $data, "true");
        return Platform_Data::getDataObject($jsonObj);
    }

    public function saveUser($data) {
        $jsonObj = $this->myWrapper->request('users/add', 'POST', $data, "true");
        return Platform_Data::getDataObject($jsonObj);
    }

}
