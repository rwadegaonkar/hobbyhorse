<?php
/**
 * Created by JetBrains PhpStorm.
 * User: radhika
 * Date: 3/18/12
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
require_once('Zend/Http/Client.php');
require_once('Platform/Data/Users.php');
require_once('Platform/Data/Users/User.php');

class Platform_Webservices_Wrapper
{
    /**
     *
     */
    public function __construct()
    {
    }

    public function request()
    {
        $config = array(
            'adapter'       => 'Zend_Http_Client_Adapter_Socket',
            'ssltransport'  => 'tls'
        );

        $client = new Zend_Http_Client('http://192.168.1.3:8080/eggsy/app/users/.json', $config);
        try
        {
            $response = $client->request();
            $jsonData = $this->parseZendResponse($response);
            $userData = json_decode($jsonData);
            return $this->populateUsersObject($userData);

        }
        catch(Zend_Http_Client_Adapter_Exception $e){
            echo('Request to webservice failed :'.$e->getMessage());
        }
    }

    /**
     * @param Zend_Http_Response $response
     * @return json string
     * @throws
     */
    public function parseZendResponse(Zend_Http_Response $response)
    {
        if($response->getStatus() == 200) {
            return $response->getBody();
        }
        else {
            throw new Exception("Error: ".$response->getMessage());
        }
    }

    /**
     * @param Zend_Http_Response $response
     * @return json string
     * @throws
     */
    public function populateUsersObject(stdClass $data)
    {
        echo "<pre>";
      //  var_dump($data->users->allUsers);
        foreach($data->users as $userArr)
        {
            $usersObj = new Platform_Data_Users();
            if(is_array($userArr))
            {
                $i = 1;
                foreach($userArr as $userJsonObj)
                {
                    $usersObj->{"user$i"} = $this->populateUserObject($userJsonObj);
                    $i++;
                }
            }
        }
        return $usersObj;
     //   print_r($usersObj);exit;
    }

    public function populateUserObject($userJsonObj)
    {
        $userObj = new Platform_Data_Users_User();
        foreach($userJsonObj as $key=>$val)
        {
            $userObj->$key = $val;
        }
        return $userObj;
    }


}
