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
        $code = isset($_REQUEST["code"])? $_REQUEST["code"]:"";
        if (empty($code)) {
            $_SESSION['state'] = md5(uniqid(rand(), TRUE)); //CSRF protection
            $dialog_url = "https://www.facebook.com/dialog/oauth?client_id="
                    . APP_ID . "&redirect_uri=" . urlencode(REDIRECT_URL) . "&state="
                    . $_SESSION['state'] . "&scope=user_likes,user_relationships";
            $data['dialog_url'] = $dialog_url;
        }
        
        if (isset($_REQUEST['state']) && ($_REQUEST['state'] == $_SESSION['state'])) {
            $token_url = "https://graph.facebook.com/oauth/access_token?"
                    . "client_id=" . APP_ID . "&redirect_uri=" . urlencode(REDIRECT_URL)
                    . "&client_secret=" . APP_SECRET . "&code=" . $code . "&scope=user_likes,user_relationships";
            $response = file_get_contents($token_url);
            $params = null;
            parse_str($response, $params);
            $graph_url = "https://graph.facebook.com/me?access_token="
                    . $params['access_token'];
            $user = json_decode(file_get_contents($graph_url));
            $data['user'] = $user;
            $graph_url = "https://graph.facebook.com/{$user->id}/likes?access_token="
                    . $params['access_token'];
            $likes = json_decode(file_get_contents($graph_url));
            $data['likes'] = $likes;
            $_SESSION['user'] = $user;
            $_SESSION['likes'] = $likes;
            $_SESSION['loginType'] = 2;
            $dataToSend = createUserObject($user);  
            $user = $this->user_model->saveUser(json_encode($dataToSend));
        }
        $this->load->view('header');
        $this->load->view('sidebar');
        if (!isset($_SESSION['user'])) {
            $this->load->view('user/login', $data);
        } else {
            $this->load->view('user/home', $_SESSION);

        }
        $this->load->view('footer');
    }

    public function saveUser() {        
        $_SESSION['loginType'] = 1;
        $data = createUserObject($_REQUEST);       
        $user = $this->user_model->saveUser(json_encode($data));
        $_SESSION['user'] = $user;           
        header('location: '.base_url());
    }

    public function logout() {
        session_destroy();
        header('location: '.base_url());
    }

}

