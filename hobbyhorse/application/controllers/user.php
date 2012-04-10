<?php

/**
 * Created by JetBrains PhpStorm.
 * User: radhika
 * Date: 3/18/12
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */
class User extends CI_Controller {

    /**
     * constructor of User controller class
     */
    public function __construct() {
        parent::__construct();
        $this->load->model('User_Model', 'user_model');
    }

    public function index() {
        $data = $this->user_model->getUsers();
        echo "<pre>";
        print_r($data->users->data);
    }

    public function login() {
        $this->load->view('header');
        $this->load->view('sidebar');
        $this->load->view('user/login');
        $this->load->view('footer');
        }
        
    public function saveUser() {
        $data['name'] = $_REQUEST['firstName'];
        $this->user_model->saveUser(json_encode($data));
    }
}



    