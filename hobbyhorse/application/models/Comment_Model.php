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

class Comment_Model extends CI_Model {

    private $myWrapper;

    public function __construct() {
        parent::__construct();
        $this->myWrapper = new Platform_Webservices_Wrapper();
    }

    public function saveComment($data) {
        $jsonObj = $this->myWrapper->request('comments/add', 'POST', $data, "true");
        return Platform_Data::getDataObject($jsonObj);        
    }
    public function checkCommented($lessonId, $method=null) {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj = $myWrapper->request('comments/checkcommented/' . $lessonId . '/' . $_SESSION['user']->username);
        if (!$method) {
            return Platform_Data::getDataObject($jsonObj);
        } else {
            return $jsonObj;
        }
    }

}
