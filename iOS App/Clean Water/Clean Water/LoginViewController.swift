//
//  LoginViewController.swift
//  Clean Water
//
//  Created by Varun Ballari on 2/22/17.
//  Copyright © 2017 Varun Ballari. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet var emailTextField: UITextField!
    @IBOutlet var passwordTextField: UITextField!
    
    @IBOutlet var emailView: UIView!
    @IBOutlet var passwordView: UIView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        emailTextField.delegate = self
        passwordTextField.delegate = self
        emailTextField.becomeFirstResponder()


        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func cancel(_ sender: Any) {
        emailTextField.resignFirstResponder()
        self.dismiss(animated: true, completion: nil)
    }

    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        // Try to find next responder
        if textField == emailTextField {
            emailTextField.resignFirstResponder()
            passwordTextField.becomeFirstResponder()
            
            emailView.backgroundColor = .gray
            passwordView.backgroundColor = .gray
            
        } else {
            passwordTextField.resignFirstResponder()
            
            emailView.tintColor = UIColor(colorLiteralRed: 209, green: 209, blue: 209, alpha: 1)
            passwordView.tintColor = UIColor(colorLiteralRed: 209, green: 209, blue: 209, alpha: 1)
            
            if (emailTextField.text == "admin" && passwordTextField.text == "password") {
                self.performSegue(withIdentifier: "goToHome", sender: self)
            } else {
                emailView.backgroundColor = .red
                passwordView.backgroundColor = .red
            }
        }
        // Do not add a line break
        return false
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
