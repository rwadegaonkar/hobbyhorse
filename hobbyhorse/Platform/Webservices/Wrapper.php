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
     *@var string
     */

    protected $_host;

    /**
     * @var string
     */
    protected $_port;

    /**
     * @var string
     */
    protected $_responseDataType;

    /**
     * @var
     */
    protected $_client;

    /**
     *
     */
    public function __construct()
    {
        $this->_responseDataType = ".json";
        $this->_host             = "http://192.168.1.3:8080";
        $this->_port             = "8080";
    }

    public function request($request_string = '',$data = null)
    {
        if($request_string == '')
            throw new Exception("Error: Request string cannot be empty");

        $config = array(
            'adapter'       => 'Zend_Http_Client_Adapter_Socket',
            'ssltransport'  => 'tls'
        );


        $this->_client = new Zend_Http_Client("http://192.168.1.3:8080/eggsy/{$request_string}/.json", $config);
        try
        {
            $response = $this->_client->request();
            $jsonData = $this->parseZendResponse($response);
            return json_decode($jsonData);

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
            throw new Exception("Error: Status is: ".$response->getStatus() . " message: ".$response->getMessage());
        }
    }

}
