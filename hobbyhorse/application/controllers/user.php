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
        $this->load->model('Lesson_Model', 'lesson_model');
    }

    public function index() {
        $data = $this->user_model->getUsers();
        echo "<pre>";
        print_r($data->users->data);
    }

    public function login() {
        $code = isset($_REQUEST["code"]) ? $_REQUEST["code"] : "";
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
            foreach ($data['likes']->data as $like) {
                $data['suggested_lessons'][] = $this->lesson_model->getSuggestedLessons($like->name, $like->category)->lessons->data;
            }
            $_SESSION['suggested_lessons'] = $data['suggested_lessons'];
            $_SESSION['loginType'] = 2;
            $dataToSend = createUserObject($user);
            $user = $this->user_model->saveUser(json_encode($dataToSend));
        }


        if (!isset($_SESSION['user'])) {
            $data['title'] = 'Hobbyhorse - Login';
            $data['header_content'] = $this->load->view('common/login_box', $data, true);
            $data['content'] = $this->load->view('user/user_login', $data, true);
            $data['sidebar'] = $this->load->view('common/right-sidebar', '', true);
            $this->load->view('templates/home', $data);
        } else {
            $data['title'] = 'Hobbyhorse - Home';
            $data['user'] = $_SESSION['user'];
            if (isset($_SESSION['likes'])) {
                $data['likes'] = $_SESSION['likes'];
            }
            $data['loginType'] = $_SESSION['loginType'];
            $data['content'] = $this->load->view('user/home', $data, true);
            $data['sidebar'] = $this->load->view('common/right-sidebar', '', true);
            $this->load->view('templates/main', $data);
        }
    }

    public function saveUser() {
        $_SESSION['loginType'] = 1;
        $data = createUserObject($_REQUEST);
        $user = $this->user_model->saveUser(json_encode($data));
        if (!empty($user->data[0])) {
            $_SESSION['user'] = $user->users->user;
            $skills = explode("^:^", $_SESSION['user']->skills);
            $hobbies = explode("^:^", $_SESSION['user']->hobbies);
            $combination = array();
            foreach ($skills as $skill) {
                foreach ($hobbies as $hobby) {
                    if ($hobby != $skill) {
                        $data['suggested_lessons'][] = $this->lesson_model->getSuggestedLessons($skill, $hobby)->lessons->data;
                    }
                }
            }
            $_SESSION['suggested_lessons'] = $data['suggested_lessons'];
            $data['title'] = 'Hobbyhorse - Home';
            $data['user'] = $_SESSION['user'];
            $data['content'] = $this->load->view('user/home', $data, true);
            $data['sidebar'] = $this->load->view('common/right-sidebar', '', true);
            $this->load->view('templates/main', $data);
        } else {
            $code = isset($_REQUEST["code"]) ? $_REQUEST["code"] : "";
            if (empty($code)) {
                $_SESSION['state'] = md5(uniqid(rand(), TRUE)); //CSRF protection
                $dialog_url = "https://www.facebook.com/dialog/oauth?client_id="
                        . APP_ID . "&redirect_uri=" . urlencode(REDIRECT_URL) . "&state="
                        . $_SESSION['state'] . "&scope=user_likes,user_relationships";
                $data['dialog_url'] = $dialog_url;
            }
            $data['error'] = "User Already Exists !";
            $data['title'] = 'Hobbyhorse - Login';
            $data['header_text'] = 'Login';
            $data['header_content'] = $this->load->view('common/login_box', $data, true);
            $data['content'] = $this->load->view('user/user_login', $data, true);
            $data['sidebar'] = $this->load->view('common/right-sidebar', '', true);
            $this->load->view('templates/home', $data);
        }
    }

    public function logout() {
        session_destroy();
        header('location: ' . base_url());
    }

    public function checkLogin() {
        $data['username'] = $_REQUEST['username'];
        $data['password'] = $_REQUEST['password'];
        $data['loginTypeId'] = 2;
        $data['createdBy'] = "admin";
        $data['lastUpdatedBy'] = "admin";
        $data['email'] = "abc";
        $data['skills'] = "abc";
        $data['location'] = "abc";
        $data['hobbies'] = "abc";
        $data['name'] = "abc";
        $userData = $this->user_model->checkLogin(json_encode($data));

        if (!empty($userData->users->data[0])) {
            $_SESSION['user'] = $userData->users->user;
            $_SESSION['loginType'] = 1;
            $data['title'] = 'Hobbyhorse - Home';
            $data['user'] = $_SESSION['user'];
            $skills = explode("^:^", $_SESSION['user']->skills);
            $hobbies = explode("^:^", $_SESSION['user']->hobbies);
            $combination = array();
            foreach ($skills as $skill) {
                foreach ($hobbies as $hobby) {
                    if ($hobby != $skill) {
                        $data['suggested_lessons'][] = $this->lesson_model->getSuggestedLessons($skill, $hobby)->lessons->data;
                    }
                }
            }
            $_SESSION['suggested_lessons'] = $data['suggested_lessons'];
            $data['content'] = $this->load->view('user/home', $data, true);
            $data['sidebar'] = $this->load->view('common/right-sidebar', '', true);
            $this->load->view('templates/main', $data);
        } else {
            $code = isset($_REQUEST["code"]) ? $_REQUEST["code"] : "";
            if (empty($code)) {
                $_SESSION['state'] = md5(uniqid(rand(), TRUE)); //CSRF protection
                $dialog_url = "https://www.facebook.com/dialog/oauth?client_id="
                        . APP_ID . "&redirect_uri=" . urlencode(REDIRECT_URL) . "&state="
                        . $_SESSION['state'] . "&scope=user_likes,user_relationships";
                $data['dialog_url'] = $dialog_url;
            }
            $data['error'] = "Authentication Failed!";
            $data['title'] = 'Hobbyhorse - Login';
            $data['header_text'] = 'Login';
            $data['header_content'] = $this->load->view('common/login_box', $data, true);
            $data['content'] = $this->load->view('user/user_login', $data, true);
            $data['sidebar'] = $this->load->view('common/right-sidebar', '', true);
            $this->load->view('templates/home', $data);
        }
    }

    function getUserByUsername($userName) {
        $userData = $this->user_model->getUserByUsername($userName, "ajax");
        echo(json_encode($userData->users));
    }

}

