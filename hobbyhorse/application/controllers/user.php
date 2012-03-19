<?php
/**
 * Created by JetBrains PhpStorm.
 * User: radhika
 * Date: 3/18/12
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */
class User extends CI_Controller
{
    /**
     * constructor of User controller class
     */

    public function __construct()
    {
        parent::__construct();
        $this->load->model('User_Model','user_model');
        $this->user_model->getUsers();
    }

    public function index()
    {
        $this->load->view('welcome_message');
    }
}
